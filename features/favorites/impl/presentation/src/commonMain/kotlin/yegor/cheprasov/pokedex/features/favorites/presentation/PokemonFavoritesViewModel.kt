package yegor.cheprasov.pokedex.features.favorites.presentation

import io.github.v1rusdev.simplemvi.compose.MviViewModel

class PokemonFavoritesViewModel :
    MviViewModel<PokemonFavoritesStateUi, PokemonFavoritesIntentUi, PokemonFavoritesEffectUi>(
        initialState = PokemonFavoritesStateUi,
    )
