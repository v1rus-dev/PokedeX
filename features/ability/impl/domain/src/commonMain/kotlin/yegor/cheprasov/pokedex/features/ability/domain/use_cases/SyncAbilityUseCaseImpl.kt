package yegor.cheprasov.pokedex.features.ability.domain.use_cases

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.features.ability.domain.repository.AbilityRepository
import yegor.cheprasov.pokedex.features.ability.use_cases.SyncAbilityUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataKey
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class SyncAbilityUseCaseImpl(
    private val repository: AbilityRepository,
) : SyncAbilityUseCase {
    override val key: SyncDataKey = SyncDataKey.ABILITIES

    override fun invoke(force: Boolean): Flow<SyncDataState> = flow {
        Napier.v("Sync abilities invoke", tag = "myTag")
        val shouldSync = force || !repository.hasAbilityInDatabase().getOrDefault(false)
        if (!shouldSync) {
            emit(SyncDataState.Skipped)
            return@flow
        }

        emitAll(repository.syncAllAbilities().map(::mapState))
    }

    private fun mapState(state: yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState): SyncDataState = when (state) {
        is yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState.Error ->
            SyncDataState.Error(
                completed = state.completed,
                total = state.total,
                throwable = state.throwable,
            )

        is yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState.InProgress ->
            SyncDataState.InProgress(
                completed = state.completed,
                total = state.total,
            )

        is yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState.PartialSuccess ->
            SyncDataState.PartialSuccess(
                savedCount = state.savedCount,
                failedCount = state.failedCount,
            )

        is yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState.Started ->
            SyncDataState.Started(total = state.total)

        is yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState.Success ->
            SyncDataState.Success(savedCount = state.savedCount)
    }
}
