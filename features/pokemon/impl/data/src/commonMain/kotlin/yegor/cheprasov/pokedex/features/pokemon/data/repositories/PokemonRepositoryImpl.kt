package yegor.cheprasov.pokedex.features.pokemon.data.repositories

import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEvolutionChainLinkEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.ability.data.datasource.LocalAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.datasource.NetworkAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.LocalPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonEntityMapper
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonResponseMapper
import yegor.cheprasov.pokedex.features.pokemon.data.models.EvolutionChainLinkResponse
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonLocalModel
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
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
    private val abilityNetworkDatasource: NetworkAbilityDatasource,
    private val abilityResponseMapper: AbilityResponseMapper,
    private val localAbilityDatasource: LocalAbilityDatasource,
) : PokemonRepository {
    override suspend fun hasPokemons(): Result<Boolean> = localDatasource.hasPokemons()

    override suspend fun hasEvolutionChains(): Result<Boolean> = localDatasource.hasEvolutionChains()

    override suspend fun getPokemon(pokemonName: String): Result<PokemonModel> {
        val normalizedName = pokemonName.lowercase()

        return localDatasource.getPokemonByName(normalizedName).fold(
            onSuccess = { existingEntity ->
                if (existingEntity != null) {
                    hydratePokemonAbilitiesIfNeeded(existingEntity).map(pokemonEntityMapper::map)
                } else {
                    fetchAndCachePokemon(pokemonName = normalizedName)
                }
            },
            onFailure = { throwable ->
                Result.failure(throwable)
            },
        )
    }

    override suspend fun getEvolutionChain(pokemonName: String): Result<List<PokemonModel>> =
        localDatasource.getEvolutionChain(pokemonName).map { chain ->
            chain.map(pokemonEntityMapper::map)
        }

    override fun observeAllPokemons(): Flow<List<PokemonLiteModel>> {
        return localDatasource.observeAllPokemons().map { entities ->
            entities.map(pokemonEntityMapper::mapLite)
        }
    }

    override suspend fun getAllPokemons(): Result<List<PokemonLiteModel>> =
        localDatasource.getAllPokemons().map { entities ->
            entities.map(pokemonEntityMapper::mapLite)
        }

    override fun searchPokemonsByName(search: String): Flow<List<PokemonLiteModel>> =
        localDatasource.observePokemonsBySearch(search).map { entities ->
            entities.map(pokemonEntityMapper::mapLite)
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
                            send(
                                SyncAllPokemonsState.InProgress(
                                    completed = current,
                                    total = total,
                                )
                            )

                            entity
                        }
                    }.awaitAll().filterNotNull()
                }
                entities += batchEntities
            }

            localDatasource.replaceAllPokemons(entities.sortedBy { it.pokemon.id })
                .onSuccess {
                    Napier.v("Successfully saved pokemons: ${entities.size}", tag = TAG)
                }
                .onFailure {
                    Napier.v("Can't updating pokemons with error: $it", tag = TAG)
                }

            val savedCount = entities.size
            val failedCount = total - savedCount

            if (failedCount > 0) {
                send(
                    SyncAllPokemonsState.PartialSuccess(
                        savedCount = savedCount,
                        failedCount = failedCount,
                    )
                )
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

    @OptIn(ExperimentalAtomicApi::class)
    override fun syncAllEvolutionChains(): Flow<SyncAllPokemonsState> = channelFlow {
        val listResult = networkDatasource.getAllEvolutionChainList(EVOLUTION_CHAIN_LIMIT).asResult()
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

        val chainIds = (1..listResponse.count).toList()
        val total = chainIds.size

        if (total == 0) {
            send(SyncAllPokemonsState.Success(savedCount = 0))
            return@channelFlow
        }

        send(SyncAllPokemonsState.Started(total = total))

        val completed = AtomicInt(0)

        try {
            val links = mutableListOf<PokemonEvolutionChainLinkEntity>()

            for (batch in chainIds.chunked(MAX_CONCURRENT_REQUESTS)) {
                val batchLinks = coroutineScope {
                    batch.map { chainId ->
                        async {
                            val response = networkDatasource.getEvolutionChain(chainId)
                                .asResult()
                                .getOrNull() ?: return@async null

                            val chainLinks = flattenEvolutionChain(
                                chainId = response.id,
                                chain = response.chain,
                            )

                            val current = completed.incrementAndFetch()
                            send(
                                SyncAllPokemonsState.InProgress(
                                    completed = current,
                                    total = total,
                                )
                            )

                            chainLinks
                        }
                    }.awaitAll().filterNotNull().flatten()
                }

                links += batchLinks
            }

            localDatasource.replaceAllEvolutionChains(links)
                .onSuccess {
                    Napier.v("Successfully saved evolution chains: ${links.distinctBy { it.chainId }.size}", tag = TAG)
                }
                .onFailure {
                    Napier.v("Can't updating evolution chains with error: $it", tag = TAG)
                }

            val savedChainCount = links.map(PokemonEvolutionChainLinkEntity::chainId).distinct().size
            val failedCount = total - savedChainCount

            if (failedCount > 0) {
                send(
                    SyncAllPokemonsState.PartialSuccess(
                        savedCount = savedChainCount,
                        failedCount = failedCount,
                    )
                )
            } else {
                send(SyncAllPokemonsState.Success(savedCount = savedChainCount))
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

    override fun observePokemon(pokemonName: String): Flow<PokemonModel> =
        localDatasource.observePokemon(pokemonName).map(pokemonEntityMapper::map)

    override fun observePokemonIsFavorite(pokemonName: String): Flow<Boolean> = observePokemon(pokemonName).map { it.isFavorite }

    override suspend fun updateFavoriteState(
        pokemonName: String,
        isFavorite: Boolean
    ): Result<Unit> = localDatasource.updateFavoriteState(pokemonName, isFavorite)

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
                val abilityNames = localModel.abilityLinks.map { it.abilityName }.distinct()
                hydrateAbilities(abilityNames).getOrThrow()

                val cachedPokemon =
                    localDatasource.getPokemonByName(localModel.pokemon.name).getOrThrow()
                requireNotNull(cachedPokemon) {
                    "Pokemon ${localModel.pokemon.name} was not found after local cache update."
                }

                pokemonEntityMapper.map(cachedPokemon)
            }
    }

    private suspend fun hydratePokemonAbilitiesIfNeeded(
        pokemon: PokemonWithRelationsEntity,
    ): Result<PokemonWithRelationsEntity> {
        val missingAbilityNames = pokemon.abilityLinks.map { it.abilityName }.toSet() -
                pokemon.abilities.map { it.name }.toSet()
        if (missingAbilityNames.isEmpty()) {
            return Result.success(pokemon)
        }

        return hydrateAbilities(missingAbilityNames).mapCatching {
            val refreshedPokemon =
                localDatasource.getPokemonByName(pokemon.pokemon.name).getOrThrow()
            requireNotNull(refreshedPokemon) {
                "Pokemon ${pokemon.pokemon.name} was not found after ability hydration."
            }
        }
    }

    private suspend fun hydrateAbilities(abilityNames: Collection<String>): Result<Unit> {
        return runCatching {
            abilityNames.distinct().forEach { abilityName ->
                val ability = abilityNetworkDatasource.getAbility(abilityName)
                    .asResult()
                    .map(abilityResponseMapper::map)
                    .getOrThrow()

                localAbilityDatasource.upsert(ability).getOrThrow()
            }
        }
    }

    private fun flattenEvolutionChain(
        chainId: Int,
        chain: EvolutionChainLinkResponse,
    ): List<PokemonEvolutionChainLinkEntity> {
        val names = mutableListOf<String>()
        collectEvolutionNames(chain = chain, names = names)

        return names.distinct().mapIndexed { index, name ->
            PokemonEvolutionChainLinkEntity(
                chainId = chainId,
                pokemonName = name,
                slot = index,
            )
        }
    }

    private fun collectEvolutionNames(
        chain: EvolutionChainLinkResponse,
        names: MutableList<String>,
    ) {
        names += chain.species.name.lowercase()
        chain.evolvesTo.forEach { nextChain ->
            collectEvolutionNames(chain = nextChain, names = names)
        }
    }

    private companion object {
        const val EVOLUTION_CHAIN_LIMIT = 600
        const val MAX_CONCURRENT_REQUESTS = 64

        private const val TAG = "PokemonRepository"
    }
}
