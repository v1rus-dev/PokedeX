package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object PokemonDetailsStateUi : StateUi

sealed interface PokemonDetailsActionUi : ActionUi

sealed interface PokemonDetailsEventUi : EventUi