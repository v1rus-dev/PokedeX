package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

internal fun mapSyncAllPokemonsState(state: SyncAllPokemonsState): SyncDataState = when (state) {
    is SyncAllPokemonsState.Error ->
        SyncDataState.Error(
            completed = state.completed,
            total = state.total,
            throwable = state.throwable,
        )

    is SyncAllPokemonsState.InProgress ->
        SyncDataState.InProgress(
            completed = state.completed,
            total = state.total,
        )

    is SyncAllPokemonsState.PartialSuccess ->
        SyncDataState.PartialSuccess(
            savedCount = state.savedCount,
            failedCount = state.failedCount,
        )

    is SyncAllPokemonsState.Started ->
        SyncDataState.Started(total = state.total)

    is SyncAllPokemonsState.Success ->
        SyncDataState.Success(savedCount = state.savedCount)
}
