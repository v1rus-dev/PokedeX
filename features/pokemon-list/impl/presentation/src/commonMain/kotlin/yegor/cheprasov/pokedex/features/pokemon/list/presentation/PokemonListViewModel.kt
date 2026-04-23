package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonListUseCase
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val pokemonMapper: PokemonModelToUiModelMapper
) : MviViewModel<PokemonListStateUi, PokemonListActionUi, PokemonListEventUi>(initialState = PokemonListStateUi) {

    init {
        Napier.v("Pokemon mapper: ${pokemonMapper::class}")
    }

    val pokemonPagingDataFlow: Flow<PagingData<PokemonUiModel>> = getPokemonListUseCase(PAGE_SIZE)
        .map { pagingData -> pagingData.map(pokemonMapper::map) }
        .cachedIn(viewModelScope)

    override fun onAction(action: PokemonListActionUi) {
        when (action) {
            PokemonListActionUi.OnBackClick -> closeScreen()
            is PokemonListActionUi.ClickPokemon -> clickPokemon(action.name)
        }
    }

    private fun closeScreen() {
        sendEvent(PokemonListEventUi.CloseScreen)
    }

    private fun clickPokemon(name: String) {
        sendEvent(PokemonListEventUi.NavigateToPokemonDetail(name))
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}
