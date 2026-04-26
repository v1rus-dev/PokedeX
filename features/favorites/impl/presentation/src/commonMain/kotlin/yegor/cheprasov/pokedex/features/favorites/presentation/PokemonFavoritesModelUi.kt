package yegor.cheprasov.pokedex.features.favorites.presentation

import io.github.v1rusdev.simplemvi.core.EffectUi
import io.github.v1rusdev.simplemvi.core.IntentUi
import io.github.v1rusdev.simplemvi.core.StateUi


data object PokemonFavoritesStateUi : StateUi

sealed interface PokemonFavoritesIntentUi : IntentUi

sealed interface PokemonFavoritesEffectUi : EffectUi
