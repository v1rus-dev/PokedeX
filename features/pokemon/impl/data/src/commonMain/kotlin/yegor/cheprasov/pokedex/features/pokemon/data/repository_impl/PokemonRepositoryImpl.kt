package yegor.cheprasov.pokedex.features.pokemon.data.repository_impl

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.LocalPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonEntityMapper
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonLocalModel
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class PokemonRepositoryImpl(
    private val networkDatasource: NetworkPokemonDatasource,
    private val localDatasource: LocalPokemonDatasource,
    private val pokemonResponseMapper: PokemonResponseMapper,
    private val pokemonEntityMapper: PokemonEntityMapper,
) : PokemonRepository {
    override suspend fun hasPokemons(): Result<Boolean> = localDatasource.hasPokemons()

    override suspend fun getPokemon(pokemonName: String): Result<PokemonModel> {
        val normalizedName = pokemonName.lowercase()

        return localDatasource.getPokemonByName(normalizedName).fold(
            onSuccess = { existingEntity ->
                if (existingEntity != null) {
                    Result.success(pokemonEntityMapper.map(existingEntity))
                } else {
                    fetchAndCachePokemon(pokemonName = normalizedName)
                }
            },
            onFailure = { throwable ->
                Result.failure(throwable)
            },
        )
    }

    override fun observeAllPokemons(): Flow<List<PokemonModel>> {
        return localDatasource.observeAllPokemons().map { entities ->
            entities.map(pokemonEntityMapper::map)
        }
    }

    override fun searchPokemonsByName(search: String): Flow<List<PokemonModel>> =
        localDatasource.observePokemonsBySearch(search).map { entities ->
            entities.map(pokemonEntityMapper::map)
        }

    @OptIn(ExperimentalAtomicApi::class)
    override fun syncAllPokemons(): Flow<SyncAllPokemonsState> = channelFlow {
        val listResult = networkDatasource.getAllPokemonList().asResult()
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

        val pokemonNames = listResponse.results.map { it.name }
        val total = pokemonNames.size

        if (total == 0) {
            send(SyncAllPokemonsState.Success(savedCount = 0))
            return@channelFlow
        }

        send(SyncAllPokemonsState.Started(total = total))

        val completed = AtomicInt(0)
        val existingFavorites: Set<String> = try {
            localDatasource.getFavoritePokemons().getOrThrow().map(PokemonEntity::name).toSet()
        } catch (e: Exception) {
            emptySet()
        }

        try {
            val entities = mutableListOf<PokemonLocalModel>()

            for (batch in pokemonNames.chunked(MAX_CONCURRENT_REQUESTS)) {
                val batchEntities = coroutineScope {
                    batch.map { name ->
                        async {
                            val response = networkDatasource.getPokemon(name)
                                .asResult()
                                .getOrNull() ?: return@async null

                            val entity = pokemonResponseMapper.map(
                                input = response,
                                isFavorite = name in existingFavorites,
                            )

                            val current = completed.incrementAndFetch()
                            send(SyncAllPokemonsState.InProgress(
                                completed = current,
                                total = total,
                            ))

                            entity
                        }
                    }.awaitAll().filterNotNull()
                }
                entities += batchEntities
            }

            localDatasource.replaceAllPokemons(entities.sortedBy { it.pokemon.id }).getOrThrow()

            val savedCount = entities.size
            val failedCount = total - savedCount

            if (failedCount > 0) {
                send(SyncAllPokemonsState.PartialSuccess(
                    savedCount = savedCount,
                    failedCount = failedCount,
                ))
            } else {
                send(SyncAllPokemonsState.Success(savedCount = savedCount))
            }
        } catch (throwable: Throwable) {
            send(
                SyncAllPokemonsState.Error(
                    completed = completed.load(),
                    total = total,
                    throwable = throwable,
                )
            )
        }
    }

    private suspend fun fetchAndCachePokemon(pokemonName: String): Result<PokemonModel> {
        return networkDatasource.getPokemon(pokemonName)
            .asResult()
            .map { response ->
                pokemonResponseMapper.map(
                    input = response,
                    isFavorite = false,
                )
            }
            .mapCatching { localModel ->
                localDatasource.upsert(localModel).getOrThrow()

                val cachedPokemon = localDatasource.getPokemonByName(localModel.pokemon.name).getOrThrow()
                requireNotNull(cachedPokemon) {
                    "Pokemon ${localModel.pokemon.name} was not found after local cache update."
                }

                pokemonEntityMapper.map(cachedPokemon)
            }
    }

    private companion object {
        const val MAX_CONCURRENT_REQUESTS = 64
    }
}
