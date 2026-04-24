package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.design.mvi.MviViewModel
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonPagedUseCase
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeUiModelToModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonPagedUseCase,
    private val pokemonMapper: PokemonModelToUiModelMapper,
    private val pokemonTypeUiModelToModel: PokemonTypeUiModelToModel
) : MviViewModel<PokemonListStateUi, PokemonListActionUi, PokemonListEventUi>(initialState = PokemonListStateUi) {

    private val mutableSearchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pokemonPagingDataFlow: Flow<PagingData<PokemonUiModel>> =
        mutableSearchQuery.debounce(300L)
            .flowOn(Dispatchers.IO)
            .flatMapLatest { query ->
                getPokemonListUseCase(query).map { pagingData ->
                    withContext(Dispatchers.Default) {
                        pagingData.map(pokemonMapper::map)
                    }
                }
            }
            .cachedIn(viewModelScope)

    override fun onAction(action: PokemonListActionUi) {
        when (action) {
            PokemonListActionUi.OnBackClick -> closeScreen()
            is PokemonListActionUi.SearchQueryChanged -> updateSearchQuery(action.query)
            is PokemonListActionUi.ClickPokemon -> clickPokemon(action.pokemonUiModel)
        }
    }

    private fun closeScreen() {
        sendEvent(PokemonListEventUi.CloseScreen)
    }

    private fun clickPokemon(pokemon: PokemonUiModel) {
        sendEvent(
            PokemonListEventUi.NavigateToPokemonDetail(
                pokemon.name,
                pokemonTypeUiModelToModel.map(pokemon.mainType)
            )
        )
    }

    private fun updateSearchQuery(query: String) {
        mutableSearchQuery.value = query.trim()
    }
}
