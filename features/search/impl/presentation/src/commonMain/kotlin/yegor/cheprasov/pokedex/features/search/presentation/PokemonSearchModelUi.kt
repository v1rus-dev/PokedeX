package yegor.cheprasov.pokedex.features.search.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object PokemonSearchStateUi : StateUi

sealed interface PokemonSearchActionUi : ActionUi

sealed interface PokemonSearchEventUi : EventUi
