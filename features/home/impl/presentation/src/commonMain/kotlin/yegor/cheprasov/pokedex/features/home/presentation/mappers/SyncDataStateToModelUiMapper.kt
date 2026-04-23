package yegor.cheprasov.pokedex.features.home.presentation.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi.*
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

class SyncDataStateToModelUiMapper : Mapper<SyncDataState, SyncAllPokemonsStateModelUi> {
    override fun map(input: SyncDataState): SyncAllPokemonsStateModelUi = when (input) {
        is SyncDataState.Error -> Error(input.completed, input.total, input.throwable)
        is SyncDataState.InProgress -> InProgress(input.completed, input.total)
        is SyncDataState.PartialSuccess -> PartialSuccess(input.savedCount, input.failedCount)
        is SyncDataState.Started -> Started(input.total)
        is SyncDataState.Success -> Success(input.savedCount)
        SyncDataState.Skipped -> Success(savedCount = 0)
    }
}
