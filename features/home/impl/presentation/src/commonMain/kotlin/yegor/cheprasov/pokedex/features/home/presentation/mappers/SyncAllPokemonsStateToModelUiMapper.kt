package yegor.cheprasov.pokedex.features.home.presentation.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi.*
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

class SyncAllPokemonsStateToModelUiMapper :
    Mapper<SyncAllPokemonsState, SyncAllPokemonsStateModelUi> {
    override fun map(input: SyncAllPokemonsState): SyncAllPokemonsStateModelUi = when(input) {
        is SyncAllPokemonsState.Error -> Error(input.completed, input.total, input.throwable)
        is SyncAllPokemonsState.InProgress -> InProgress(input.completed, input.total)
        is SyncAllPokemonsState.Started -> Started(input.total)
        is SyncAllPokemonsState.Success -> Success(input.savedCount)
        is SyncAllPokemonsState.PartialSuccess -> PartialSuccess(input.savedCount, input.failedCount)
    }
}