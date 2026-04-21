package yegor.cheprasov.pokedex.features.home.presentation.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

class SyncAllPokemonsStateToModelUiMapper :
    Mapper<SyncAllPokemonsState, SyncAllPokemonsStateModelUi> {
    override fun map(input: SyncAllPokemonsState): SyncAllPokemonsStateModelUi = when(input) {
        is SyncAllPokemonsState.Error -> SyncAllPokemonsStateModelUi.Error(input.completed, input.total, input.throwable)
        is SyncAllPokemonsState.InProgress -> SyncAllPokemonsStateModelUi.InProgress(input.completed, input.total)
        is SyncAllPokemonsState.Started -> SyncAllPokemonsStateModelUi.Started(input.total)
        is SyncAllPokemonsState.Success -> SyncAllPokemonsStateModelUi.Success(input.savedCount)
    }
}