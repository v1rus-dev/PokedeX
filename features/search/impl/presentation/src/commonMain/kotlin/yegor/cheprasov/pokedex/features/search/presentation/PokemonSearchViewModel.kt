package yegor.cheprasov.pokedex.features.search.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel

class PokemonSearchViewModel :
    MviViewModel<PokemonSearchStateUi, PokemonSearchActionUi, PokemonSearchEventUi>(
        initialState = PokemonSearchStateUi,
    ) {
    override fun onAction(action: PokemonSearchActionUi) {
        when (action) {
            PokemonSearchActionUi.OnBackClicked -> sendEvent(PokemonSearchEventUi.CloseScreen)
        }
    }
}
