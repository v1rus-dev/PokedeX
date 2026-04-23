package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutCacheWindow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.compose.koinInject
import yegor.cheprasov.pokedex.core.design.composable.effects.CollectEventsUiEffect
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable.PokemonListScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonListDestination(
    navigator: AppNavigator = koinInject(),
    viewModel: PokemonListViewModel = koinViewModel(),
) {
    val lazyPagingItems = viewModel.pokemonPagingDataFlow.collectAsLazyPagingItems()
    val cacheWindow = LazyLayoutCacheWindow(aheadFraction = 0.5f, behindFraction = 0.3f)
    val lazyListState = rememberLazyListState(cacheWindow = cacheWindow)

    PokemonListScreen(
        listState = lazyListState,
        lazyPagingItems = lazyPagingItems,
        onAction = viewModel::onAction,
    )

    CollectEventsUiEffect(viewModel.uiEvents) { event ->
        when(event) {
            PokemonListEventUi.CloseScreen -> navigator.popBackStack()
            is PokemonListEventUi.NavigateToPokemonDetail -> {
                navigator.navigate(PokemonDetails(pokemonName = event.name))
            }
        }
    }
}
