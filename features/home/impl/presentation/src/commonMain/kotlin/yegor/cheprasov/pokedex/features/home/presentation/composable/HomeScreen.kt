package yegor.cheprasov.pokedex.features.home.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokedex
import pokedex.core.resources.generated.resources.search
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.pull_to_refresh.PokedexPullToRefresh
import yegor.cheprasov.pokedex.core.design.composable.pull_to_refresh.PokedexPullToRefreshIndicator
import yegor.cheprasov.pokedex.core.design.composable.pull_to_refresh.rememberPokedexPullToRefreshState
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextField
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.home.presentation.HomeActionUi
import yegor.cheprasov.pokedex.features.home.presentation.HomeStateUi
import yegor.cheprasov.pokedex.features.home.presentation.composable.components.HomeCard
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardModelUi
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi

@Composable
internal fun HomeScreen(state: HomeStateUi, onAction: (HomeActionUi) -> Unit) {
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPokedexPullToRefreshState()
    val isSyncInProgress =
        state.syncAllPokemonsStateModelUi is SyncAllPokemonsStateModelUi.Started ||
                state.syncAllPokemonsStateModelUi is SyncAllPokemonsStateModelUi.InProgress

    Scaffold(
        topBar = {
            PokedexTopAppBar(title = stringResource(Res.string.pokedex))
        },
        containerColor = PokedexTheme.colors.background,
    ) {
        PokedexPullToRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            isRefreshing = isSyncInProgress,
            onRefresh = {
                onAction(HomeActionUi.OnRefreshPokemons)
            },
            state = pullToRefreshState,
            indicator = PokedexPullToRefreshIndicator.Progress(
                progressPercent = state.syncAllPokemonsStateModelUi?.percent ?: 0
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.padding(top = PokedexTheme.spacing.large))
                TextField(
                    enabled = false,
                    modifier = Modifier
                        .padding(horizontal = PokedexTheme.spacing.large)
                        .localSharedElement(key = "home-search-text-field"),
                    onClick = {
                        onAction.invoke(HomeActionUi.OnSearchClick)
                    },
                    hint = stringResource(Res.string.search)
                )
                Spacer(modifier = Modifier.height(20.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PokedexTheme.spacing.large),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    maxItemsInEachRow = 2,
                ) {
                    HomeMainCardModelUi.items.forEach { card ->
                         HomeCard(
                            model = card,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onAction(HomeActionUi.OnClickMainHomeCard(card.type))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PokedexTheme {
        HomeScreen(state = HomeStateUi(), onAction = {})
    }
}
