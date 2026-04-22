package yegor.cheprasov.pokedex.features.ability.data.repositories

import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.ability.data.datasource.LocalAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.datasource.NetworkAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityEntityMapper
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityResponseMapper
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityLocalModel
import yegor.cheprasov.pokedex.features.ability.domain.repository.AbilityRepository
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel
import yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class AbilityRepositoryImpl(
    private val localDatasource: LocalAbilityDatasource,
    private val abilityEntityMapper: AbilityEntityMapper,
    private val networkDatasource: NetworkAbilityDatasource,
    private val abilityResponseMapper: AbilityResponseMapper,
) : AbilityRepository {
    override suspend fun getAllAbilities(): Result<List<AbilityModel>> = localDatasource.getAllAbilities().map { entities ->
        entities.map(abilityEntityMapper::map)
    }

    override fun observeAllAbilities(): Flow<List<AbilityModel>> =
        localDatasource.observeAllAbilities().map { entities ->
            entities.map(abilityEntityMapper::map)
        }

    override suspend fun hasAbilityInDatabase(): Result<Boolean> = localDatasource.hasAbilityInDatabase()

    @OptIn(ExperimentalAtomicApi::class)
    override fun syncAllAbilities(): Flow<SyncAllAbilitiesState> = channelFlow {
        val listResult = networkDatasource.getAllAbilityList(ABILITIES_LIMIT).asResult()
        val listResponse = listResult.getOrNull()
        if (listResponse == null) {
            send(
                SyncAllAbilitiesState.Error(
                    completed = 0,
                    total = 0,
                    throwable = requireNotNull(listResult.exceptionOrNull()),
                )
            )
            return@channelFlow
        }

        val abilityNames = listResponse.results.map { it.name.lowercase() }.distinct()
        val total = abilityNames.size

        if (total == 0) {
            send(SyncAllAbilitiesState.Success(savedCount = 0))
            return@channelFlow
        }

        send(SyncAllAbilitiesState.Started(total = total))

        val completed = AtomicInt(0)

        try {
            val abilities = mutableListOf<AbilityLocalModel>()

            for (batch in abilityNames.chunked(MAX_CONCURRENT_REQUESTS)) {
                val batchAbilities = coroutineScope {
                    batch.map { abilityName ->
                        async {
                            val ability = networkDatasource.getAbility(abilityName)
                                .asResult()
                                .map(abilityResponseMapper::map)
                                .getOrNull() ?: return@async null

                            val current = completed.incrementAndFetch()
                            send(
                                SyncAllAbilitiesState.InProgress(
                                    completed = current,
                                    total = total,
                                )
                            )

                            ability
                        }
                    }.awaitAll().filterNotNull()
                }

                abilities += batchAbilities
            }

            localDatasource.replaceAllAbilities(abilities)
                .onSuccess {
                    Napier.v("Successfully saving abilities", tag = TAG)
                }
                .onFailure {
                    Napier.v("Can't save abilities with error: $it", tag = TAG)
                }

            val savedCount = abilities.size
            val failedCount = total - savedCount

            if (failedCount > 0) {
                send(
                    SyncAllAbilitiesState.PartialSuccess(
                        savedCount = savedCount,
                        failedCount = failedCount,
                    )
                )
            } else {
                send(SyncAllAbilitiesState.Success(savedCount = savedCount))
            }
        } catch (throwable: Throwable) {
            send(
                SyncAllAbilitiesState.Error(
                    completed = completed.load(),
                    total = total,
                    throwable = throwable,
                )
            )
        }
    }

    private companion object {
        private const val TAG = "AbilityRepository"
        const val ABILITIES_LIMIT = 500
        const val MAX_CONCURRENT_REQUESTS = 64
    }
}
