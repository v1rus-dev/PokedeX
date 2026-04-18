package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonListUseCase

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
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
