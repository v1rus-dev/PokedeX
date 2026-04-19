package yegor.cheprasov.pokedex.features.favorites.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object PokemonFavoritesStateUi : StateUi

sealed interface PokemonFavoritesActionUi : ActionUi

sealed interface PokemonFavoritesEventUi : EventUi
