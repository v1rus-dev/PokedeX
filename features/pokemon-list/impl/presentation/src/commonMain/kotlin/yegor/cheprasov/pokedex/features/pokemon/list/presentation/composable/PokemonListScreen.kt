package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.search_pokemon
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexSearchTopAppBar
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
    val scrollState = rememberScrollState()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        containerColor = colors.background,
        topBar = {
            PokedexSearchTopAppBar(
                textFieldState = textFieldState,
                hint = stringResource(Res.string.search_pokemon),
                scrollState = scrollState,
                onBack = {
                    onAction(PokemonListActionUi.OnBackClick)
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { pokemon -> pokemon.id },
            ) { index ->
                lazyPagingItems[index]?.let {
                    PokemonCard(it, onClick = {
                        onAction.invoke(PokemonListActionUi.ClickPokemon(it.name))
                    })
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListScreenPreview() {
    PokedexTheme {
        PokemonListScreen(
            listState = rememberLazyListState(),
            lazyPagingItems = flowOf<PagingData<PokemonUiModel>>().collectAsLazyPagingItems(),
            onAction = {},
        )
    }
}
