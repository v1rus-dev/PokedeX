package yegor.cheprasov.pokedex.features.pokemon.data.repository_impl

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonEntity
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonEntityMapper
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

class PokemonRepositoryImpl(
    private val pokemonDao: PokemonDao,
    private val datasource: NetworkPokemonDatasource,
    private val pokemonResponseMapper: PokemonResponseMapper,
    private val pokemonEntityMapper: PokemonEntityMapper,
) : PokemonRepository {
    override suspend fun hasPokemons(): Boolean = pokemonDao.hasPokemons()

    override suspend fun getPokemon(pokemonName: String): Result<PokemonModel> {
        val existingEntity = pokemonDao.getByName(pokemonName)
        existingEntity?.let { entity ->
            return Result.success(pokemonEntityMapper.map(entity))
        }

        val response = datasource.getPokemon(pokemonName)
            .asResult()
            .getOrElse { throwable ->
                return Result.failure(throwable)
            }
        val entity = pokemonResponseMapper.map(
            input = response,
            isFavorite = existingEntity?.isFavorite == true,
        )
        pokemonDao.upsert(entity)
        return Result.success(pokemonEntityMapper.map(entity))
    }

    override fun observeAllPokemons(): Flow<List<PokemonModel>> {
        return pokemonDao.observeAll().map { entities ->
            entities.map(pokemonEntityMapper::map)
        }
    }

    override fun syncAllPokemons(): Flow<SyncAllPokemonsState> = channelFlow {
        val listResult = datasource.getAllPokemonList().asResult()
        val listResponse = listResult.getOrNull()
        if (listResponse == null) {
            send(
                SyncAllPokemonsState.Error(
                    completed = 0,
                    total = 0,
                    throwable = requireNotNull(listResult.exceptionOrNull()),
                )
            )
            return@channelFlow
        }

        val total = minOf(listResponse.count, listResponse.results.size)
        val pokemonNames = listResponse.results
            .take(total)
            .map { it.name }

        send(SyncAllPokemonsState.Started(total = total))

        if (total == 0) {
            send(SyncAllPokemonsState.Success(savedCount = 0))
            return@channelFlow
        }

        val progressLock = Mutex()
        var completed = 0
        val existingFavoritesByName = pokemonDao.getAll()
            .associateBy(PokemonEntity::name, PokemonEntity::isFavorite)

        try {
            val entities = mutableListOf<PokemonEntity>()

            for (batch in pokemonNames.chunked(MAX_CONCURRENT_REQUESTS)) {
                val batchEntities = coroutineScope {
                    batch.map { name ->
                        async {
                            val response = datasource.getPokemon(name)
                                .asResult()
                                .getOrThrow()
                            val entity = pokemonResponseMapper.map(
                                input = response,
                                isFavorite = existingFavoritesByName[name] == true,
                            )
                            val progressState = progressLock.withLock {
                                completed += 1
                                SyncAllPokemonsState.InProgress(
                                    completed = completed,
                                    total = total,
                                )
                            }
                            send(progressState)
                            entity
                        }
                    }.awaitAll()
                }
                entities += batchEntities
            }

            pokemonDao.replaceAll(entities.sortedBy { it.id })
            send(SyncAllPokemonsState.Success(savedCount = entities.size))
        } catch (throwable: Throwable) {
            send(
                SyncAllPokemonsState.Error(
                    completed = completed,
                    total = total,
                    throwable = throwable,
                )
            )
        }
    }

    private companion object {
        const val MAX_CONCURRENT_REQUESTS = 64
    }
}
