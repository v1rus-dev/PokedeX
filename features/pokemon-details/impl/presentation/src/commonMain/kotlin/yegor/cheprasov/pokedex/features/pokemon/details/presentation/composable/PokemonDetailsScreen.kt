package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.composable.collapsing_header.CollapsingHeaderLayout
import yegor.cheprasov.pokedex.core.design.composable.collapsing_header.rememberCollapsingHeaderLayoutState
import yegor.cheprasov.pokedex.core.design.theme.PokedexStatusBarEffect
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsIntentUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi

@Composable
internal fun PokemonDetailsScreen(
    state: PokemonDetailsStateUi,
    onAction: (PokemonDetailsIntentUi) -> Unit
) {
    val collapsingHeaderState = rememberCollapsingHeaderLayoutState()
    val scrollState = rememberScrollState()
    val navigationBarHeight = with(LocalDensity.current) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val contentCornerRadius = 32.dp * (
        1f - collapsingHeaderState.overlapFraction.progressFromStartFraction(ContentCornerCollapseFraction)
    )
    val isBackgroundUnderStatusBar = collapsingHeaderState.overlapFraction >= 1f

    if (isBackgroundUnderStatusBar) {
        PokedexStatusBarEffect(backgroundColor = PokedexTheme.colors.background)
    } else {
        PokedexStatusBarEffect(preferLightIcons = true)
    }

    CollapsingHeaderLayout(
        state = collapsingHeaderState,
        minHeaderHeight = 76.dp,
        headerHeight = HeaderHeight,
        initialContentOverlap = 60.dp,
        backgroundColor = PokedexTheme.colors.background,
        cornerRadius = RoundedCornerShape(topStart = contentCornerRadius, topEnd = contentCornerRadius),
        header = {
            PokemonDetailsHeader(
                state,
                overlapFraction = collapsingHeaderState.overlapFraction,
                onAction = onAction
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp)
                .padding(bottom = 20.dp + navigationBarHeight),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.pokemon.stats.isNotEmpty()) {
                repeat(TestStatsCardsCount) {
                    PokemonStatsCard(state.pokemon.stats)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailsScreenPreview() {
    PokedexTheme {
        PokemonDetailsScreen(
            state = PokemonDetailsStateUi.PREVIEW,
            onAction = {}
        )
    }
}

private const val ContentCornerCollapseFraction = 0.2f
private const val TestStatsCardsCount = 6

private fun Float.progressFromStartFraction(startFractionBeforeEnd: Float): Float {
    val startFraction = 1f - startFractionBeforeEnd.coerceIn(0f, 1f)

    return if (startFraction == 1f) {
        if (this >= 1f) 1f else 0f
    } else {
        ((coerceIn(0f, 1f) - startFraction) / (1f - startFraction)).coerceIn(0f, 1f)
    }
}
