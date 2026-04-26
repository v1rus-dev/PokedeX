package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.v1rusdev.simplemvi.compose.MviViewModel
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
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonPagedUseCase
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonLiteModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeUiModelToModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonPagedUseCase,
    private val pokemonMapper: PokemonLiteModelToUiModelMapper,
    private val pokemonTypeUiModelToModel: PokemonTypeUiModelToModel
) : MviViewModel<PokemonListStateUi, PokemonListIntentUi, PokemonListEffectUi>(initialState = PokemonListStateUi) {

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

    override fun onIntent(intent: PokemonListIntentUi) {
        when (intent) {
            PokemonListIntentUi.OnBackClick -> closeScreen()
            is PokemonListIntentUi.SearchQueryChanged -> updateSearchQuery(intent.query)
            is PokemonListIntentUi.ClickPokemon -> clickPokemon(intent.pokemonUiModel)
        }
    }

    private fun closeScreen() {
        sendEffect(PokemonListEffectUi.CloseScreen)
    }

    private fun clickPokemon(pokemon: PokemonUiModel) {
        sendEffect(
            PokemonListEffectUi.NavigateToPokemonDetail(
                pokemon.name,
                pokemonTypeUiModelToModel.map(pokemon.mainType)
            )
        )
    }

    private fun updateSearchQuery(query: String) {
        mutableSearchQuery.value = query.trim()
    }
}
