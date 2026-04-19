package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import yegor.cheprasov.pokedex.core.design.mvi.ActionUi
import yegor.cheprasov.pokedex.core.design.mvi.EventUi
import yegor.cheprasov.pokedex.core.design.mvi.StateUi

data object PokemonListStateUi : StateUi

sealed interface PokemonListActionUi : ActionUi {
    data class ClickPokemon(
        val name: String,
    ) : PokemonListActionUi
}

sealed interface PokemonListEventUi : EventUi {
    data class NavigateToPokemonDetail(
        val name: String,
    ) : PokemonListEventUi
}
