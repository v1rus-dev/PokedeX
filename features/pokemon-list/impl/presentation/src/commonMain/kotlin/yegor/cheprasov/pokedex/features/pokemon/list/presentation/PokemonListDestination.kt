package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.v1rusdev.simplemvi.compose.CollectEffectsUiEvent
import org.koin.compose.koinInject
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable.PokemonListScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonListDestination(
    navigator: AppNavigator = koinInject(),
    viewModel: PokemonListViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val lazyPagingItems = viewModel.pokemonPagingDataFlow.collectAsLazyPagingItems()
    val cacheWindow = LazyLayoutCacheWindow(aheadFraction = 0.5f, behindFraction = 0.3f)
    val lazyListState = rememberLazyListState(cacheWindow = cacheWindow)

    PokemonListScreen(
        state = state,
        listState = lazyListState,
        lazyPagingItems = lazyPagingItems,
        onIntent = viewModel::onIntent,
    )

    CollectEffectsUiEvent(viewModel.uiEffects) { event ->
        when(event) {
            PokemonListEffectUi.CloseScreen -> navigator.popBackStack()
            is PokemonListEffectUi.NavigateToPokemonDetail -> {
                navigator.navigate(PokemonDetails(pokemonName = event.name, pokemonType = event.pokemonType))
            }
        }
    }
}
