package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_arrow_right
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsIntentUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonImage
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

@Composable
internal fun PokemonEvolutionPart(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    val scrollState = rememberScrollState()
    val contentHorizontalPadding = PokedexTheme.spacing.large

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val shouldScroll = state.evolutions.size >= MinScrollableEvolutionItemsCount
        val contentWidth = maxOf(0.dp, maxWidth - contentHorizontalPadding * 2)

        if (shouldScroll) {
            ScrollableEvolutionRow(
                state = state,
                horizontalPadding = contentHorizontalPadding,
                scrollState = scrollState,
                onIntent = onIntent
            )
        } else {
            StaticEvolutionRow(
                state = state,
                availableWidth = contentWidth,
                horizontalPadding = contentHorizontalPadding,
                onIntent = onIntent
            )
        }
    }
}

@Composable
private fun ScrollableEvolutionRow(
    state: PokemonDetailsStateUi,
    horizontalPadding: Dp,
    scrollState: ScrollState,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(horizontalPadding))
        state.evolutions.forEachIndexed { index, model ->
            EvolutionPokemonCard(
                pokemon = model,
                isCurrent = model.isCurrentPokemon(state.pokemon),
                cardWidth = EvolutionCardWidth,
                onIntent = onIntent
            )
            if (index != state.evolutions.lastIndex) {
                EvolutionArrow()
            }
        }
        Spacer(Modifier.width(horizontalPadding))
    }
}

@Composable
private fun StaticEvolutionRow(
    state: PokemonDetailsStateUi,
    availableWidth: Dp,
    horizontalPadding: Dp,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    if (state.evolutions.size == ThreeItemEvolutionCount) {
        ThreeItemEvolutionRow(
            state = state,
            availableWidth = availableWidth,
            horizontalPadding = horizontalPadding,
            onIntent = onIntent
        )
    } else {
        CenteredEvolutionRow(
            state = state,
            availableWidth = availableWidth,
            horizontalPadding = horizontalPadding,
            onIntent = onIntent
        )
    }
}

@Composable
private fun CenteredEvolutionRow(
    state: PokemonDetailsStateUi,
    availableWidth: Dp,
    horizontalPadding: Dp,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    val cardWidth = resolveCenteredEvolutionCardWidth(
        availableWidth = availableWidth,
        itemsCount = state.evolutions.size,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.evolutions.forEachIndexed { index, model ->
            EvolutionPokemonCard(
                pokemon = model,
                isCurrent = model.isCurrentPokemon(state.pokemon),
                cardWidth = cardWidth,
                onIntent = onIntent
            )
            if (index != state.evolutions.lastIndex) {
                EvolutionArrow()
            }
        }
    }
}

@Composable
private fun ThreeItemEvolutionRow(
    state: PokemonDetailsStateUi,
    availableWidth: Dp,
    horizontalPadding: Dp,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    val cardWidth = resolveThreeItemEvolutionCardWidth(availableWidth)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.evolutions.forEachIndexed { index, model ->
            EvolutionPokemonCard(
                pokemon = model,
                isCurrent = model.isCurrentPokemon(state.pokemon),
                cardWidth = cardWidth,
                onIntent = onIntent
            )
            if (index != state.evolutions.lastIndex) {
                Spacer(Modifier.weight(1f))
                EvolutionArrowIcon()
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun EvolutionPokemonCard(
    pokemon: PokemonUiModel,
    isCurrent: Boolean,
    cardWidth: Dp,
    modifier: Modifier = Modifier,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    val cardShape = RoundedCornerShape(18.dp)
    val surfaceColor = PokedexTheme.colors.surface

    Column(
        modifier = modifier
            .width(cardWidth)
            .height(EvolutionCardHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .cardSurface(shape = cardShape)
                .then(
                    if (isCurrent) Modifier.border(
                        width = 0.5.dp,
                        color = pokemon.mainType.colors.primary.copy(alpha = 0.2f),
                        cardShape
                    ) else {
                        Modifier
                    }
                )
                .clip(cardShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            pokemon.mainType.colors.gradientStart.copy(alpha = 0.30f),
                            pokemon.mainType.colors.primary.copy(alpha = 0.14f),
                            surfaceColor.copy(alpha = 0.84f),
                            surfaceColor,
                        )
                    )
                )
                .clickable(enabled = !isCurrent) {
                    onIntent(PokemonDetailsIntentUi.OnEvolutionClick(pokemon))
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(9.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(PokedexTheme.colors.surface.copy(alpha = 0.72f)),
                    contentAlignment = Alignment.Center
                ) {
                    PokemonImage(
                        imageUrl = pokemon.imageUrl,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = PokedexTheme.typography.titleSmall,
                    color = PokedexTheme.colors.textPrimary,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun EvolutionArrow() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(Modifier.width(ArrowHorizontalPadding))
        EvolutionArrowIcon()
        Spacer(Modifier.width(ArrowHorizontalPadding))
    }
}

@Composable
private fun EvolutionArrowIcon() {
    Icon(
        painter = painterResource(Res.drawable.ic_arrow_right),
        modifier = Modifier.size(EvolutionArrowWidth),
        contentDescription = null,
        tint = PokedexTheme.colors.textSecondary
    )
}

@Preview
@Composable
private fun PokemonEvolutionPartOneItemPreview() {
    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize().background(PokedexTheme.colors.background)) {
            PokemonEvolutionPart(state = previewEvolutionState(1), onIntent = {})
        }
    }
}

@Preview
@Composable
private fun PokemonEvolutionPartTwoItemsPreview() {
    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize().background(PokedexTheme.colors.background)) {
            PokemonEvolutionPart(state = previewEvolutionState(2), onIntent = {})
        }
    }
}

@Preview
@Composable
private fun PokemonEvolutionPartThreeItemsPreview() {
    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize().background(PokedexTheme.colors.background)) {
            PokemonEvolutionPart(state = previewEvolutionState(3), onIntent = {})
        }
    }
}

@Preview
@Composable
private fun PokemonEvolutionPartFourItemsPreview() {
    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize().background(PokedexTheme.colors.background)) {
            PokemonEvolutionPart(state = previewEvolutionState(4), onIntent = {})
        }
    }
}

private val EvolutionCardWidth = 112.dp
private val EvolutionCardHeight = 144.dp
private val EvolutionArrowWidth = 24.dp
private val ArrowHorizontalPadding = 4.dp
private const val ThreeItemEvolutionCount = 3
private const val MinScrollableEvolutionItemsCount = 4

private fun resolveCenteredEvolutionCardWidth(
    availableWidth: Dp,
    itemsCount: Int,
): Dp {
    if (itemsCount <= 0) {
        return EvolutionCardWidth
    }

    val arrowBlockWidth = ArrowHorizontalPadding * 2 + EvolutionArrowWidth
    val arrowsWidth = arrowBlockWidth * (itemsCount - 1).coerceAtLeast(0).toFloat()
    val maxCardWidthWithoutScroll = (availableWidth - arrowsWidth) / itemsCount.toFloat()

    return minOf(EvolutionCardWidth, maxOf(0.dp, maxCardWidthWithoutScroll))
}

private fun resolveThreeItemEvolutionCardWidth(availableWidth: Dp): Dp {
    val arrowsWidth = EvolutionArrowWidth * (ThreeItemEvolutionCount - 1)
    val maxCardWidthWithoutScroll = (availableWidth - arrowsWidth) / ThreeItemEvolutionCount.toFloat()

    return minOf(EvolutionCardWidth, maxOf(0.dp, maxCardWidthWithoutScroll))
}

private fun PokemonUiModel.isCurrentPokemon(currentPokemon: PokemonUiModel): Boolean {
    return (id != 0 && id == currentPokemon.id) || name.equals(
        currentPokemon.name,
        ignoreCase = true
    )
}

private fun previewEvolutionState(itemsCount: Int): PokemonDetailsStateUi {
    val preview = PokemonDetailsStateUi.PREVIEW
    val evolutions = listOf(
        preview.pokemon,
        preview.pokemon.copy(name = "Ivysaur", id = 2),
        preview.pokemon.copy(name = "Venusaur", id = 3),
        preview.pokemon.copy(name = "Mega Venusaur", id = 10033),
    )

    return preview.copy(evolutions = evolutions.take(itemsCount))
}
