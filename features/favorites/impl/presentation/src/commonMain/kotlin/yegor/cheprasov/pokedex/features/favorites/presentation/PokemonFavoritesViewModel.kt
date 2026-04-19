package yegor.cheprasov.pokedex.features.favorites.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel

class PokemonFavoritesViewModel :
    MviViewModel<PokemonFavoritesStateUi, PokemonFavoritesActionUi, PokemonFavoritesEventUi>(
        initialState = PokemonFavoritesStateUi,
    ) {

    override fun onAction(action: PokemonFavoritesActionUi) = Unit
}
