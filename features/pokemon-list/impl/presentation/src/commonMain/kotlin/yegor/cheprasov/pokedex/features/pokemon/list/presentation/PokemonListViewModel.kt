package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.list.domain.PokemonListApi

class PokemonListViewModel(
    private val pokemonListApi: PokemonListApi,
) : MviViewModel<PokemonListStateUi, PokemonListActionUi, PokemonListEventUi>(initialState = PokemonListStateUi(pokemonNames = emptyList())) {

    override fun onAction(action: PokemonListActionUi) {
        when(action) {
            is PokemonListActionUi.ClickPokemon -> clickPokemon(action.name)
        }
    }

    private fun clickPokemon(name: String) {
        sendEvent(PokemonListEventUi.NavigateToPokemonDetail(name))
    }

}
