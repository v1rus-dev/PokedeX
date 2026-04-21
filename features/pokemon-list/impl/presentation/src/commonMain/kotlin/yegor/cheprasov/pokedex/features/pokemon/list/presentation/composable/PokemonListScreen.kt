package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokedex
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListActionUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

@Composable
internal fun PokemonListScreen(
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<PokemonUiModel>,
    onAction: (PokemonListActionUi) -> Unit
) {
    val colors = PokedexTheme.colors
    Scaffold(
        containerColor = colors.background,
        topBar = {
            PokedexTopAppBar(title = stringResource(Res.string.pokedex))
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { pokemon -> pokemon.name },
                ) { index ->
                    lazyPagingItems[index]?.let { pokemon ->
                        PokemonItem(pokemon = pokemon, onAction = onAction)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListScreenPreview() {
    PokemonListScreen(
        listState = rememberLazyListState(), lazyPagingItems = flowOf<PagingData<PokemonUiModel>>().collectAsLazyPagingItems(), onAction = {})
}
