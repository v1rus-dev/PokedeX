package yegor.cheprasov.pokedex.features.home.presentation.composable

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.pokedex
import pokedex.core.resources.generated.resources.search_pokemon
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextField
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.home.presentation.HomeActionUi
import yegor.cheprasov.pokedex.features.home.presentation.HomeStateUi
import yegor.cheprasov.pokedex.features.home.presentation.composable.components.LoaderHeader
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi

private val LoaderHeaderHeight = 56.dp

@Composable
internal fun HomeScreen(state: HomeStateUi, onAction: (HomeActionUi) -> Unit) {
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()
    val loadingPercent = when (val syncState = state.syncAllPokemonsStateModelUi) {
        is SyncAllPokemonsStateModelUi.Started -> syncState.percent
        is SyncAllPokemonsStateModelUi.InProgress -> syncState.percent
        else -> null
    }
    val loaderHeaderOffset by animateDpAsState(
        targetValue = LoaderHeaderHeight * if (loadingPercent != null) 1f else pullToRefreshState.distanceFraction.coerceIn(0f, 1f),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "loaderHeaderOffset"
    )

    Scaffold(
        topBar = {
            PokedexTopAppBar(title = stringResource(Res.string.pokedex))
        },
        containerColor = PokedexTheme.colors.background,
    ) {
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize().padding(it),
            isRefreshing = loadingPercent != null,
            onRefresh = {
                Napier.v("Refresh")
                onAction(HomeActionUi.OnRefreshPokemons)
            },
            state = pullToRefreshState,
            indicator = {}
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(loaderHeaderOffset)
                ) {
                    LoaderHeader(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(horizontal = PokedexTheme.spacing.large),
                        distanceFraction = pullToRefreshState.distanceFraction,
                        loadingPercent = loadingPercent
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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
                        hint = stringResource(Res.string.search_pokemon)
                    )
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
