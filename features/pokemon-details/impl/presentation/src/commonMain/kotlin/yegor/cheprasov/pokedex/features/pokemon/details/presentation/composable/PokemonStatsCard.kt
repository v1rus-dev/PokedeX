package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import kotlin.math.min
import org.jetbrains.compose.resources.stringResource
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatValueUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatsUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.previewValueRange

@Composable
internal fun PokemonStatsCard(
    stats: List<PokemonStatValueUiModel>,
    modifier: Modifier = Modifier,
    isOpen: Boolean = false,
    pokemonType: PokemonTypeUiModel = PokemonTypeUiModel.Unknown,
    onClick: (() -> Unit)? = null
) {
    val clickModifier = onClick?.let { clickAction ->
        Modifier.clickable { clickAction() }
    } ?: Modifier

    PokemonStatsGrid(
        stats = stats,
        isOpen = isOpen,
        pokemonType = pokemonType,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .cardSurface(shape = RoundedCornerShape(16.dp))
            .then(clickModifier)
            .padding(CardContentPadding)
    )
}

@Composable
private fun PokemonStatsGrid(
    stats: List<PokemonStatValueUiModel>,
    isOpen: Boolean,
    pokemonType: PokemonTypeUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonStatsTilesGrid(stats = stats)

        AnimatedVisibility(
            visible = isOpen,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(RadarTopPadding))
                PokemonStatsRadarChart(
                    stats = stats,
                    chartColor = pokemonType.colors.primary,
                )
            }
        }
    }
}

@Composable
private fun PokemonStatsTilesGrid(
    stats: List<PokemonStatValueUiModel>,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            stats.forEach { stat ->
                PokemonStatTile(stat = stat)
            }
        },
    ) { measurables, constraints ->
        val layoutWidth = if (constraints.hasBoundedWidth) {
            constraints.maxWidth
        } else {
            DefaultCardWidth.roundToPx()
        }.coerceAtLeast(constraints.minWidth)

        val columns = calculateColumnCount(
            statsCount = measurables.size,
            availableWidth = layoutWidth,
            wideGridMinWidth = WideGridMinWidth.roundToPx(),
        )
        val gap = TileGap.roundToPx()
        val tileHeight = TileHeight.roundToPx()
        val tileWidth = if (columns == 0) {
            0
        } else {
            (layoutWidth - gap * (columns - 1)).floorDiv(columns).coerceAtLeast(0)
        }
        val placeables = measurables.map { measurable ->
            measurable.measure(
                Constraints.fixed(
                    width = tileWidth,
                    height = tileHeight,
                )
            )
        }
        val rows = if (columns == 0) {
            0
        } else {
            (placeables.size + columns - 1) / columns
        }
        val layoutHeight = constraints.constrainHeight(
            height = tileHeight * rows + gap * (rows - 1).coerceAtLeast(0)
        )

        layout(width = layoutWidth, height = layoutHeight) {
            placeables.forEachIndexed { index, placeable ->
                val row = index / columns
                val column = index % columns

                placeable.placeRelative(
                    x = column * (tileWidth + gap),
                    y = row * (tileHeight + gap),
                )
            }
        }
    }
}

@Composable
private fun PokemonStatTile(
    stat: PokemonStatValueUiModel,
    modifier: Modifier = Modifier,
) {
    val label = stringResource(stat.statsUiModel.label)
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = modifier
            .background(
                color = stat.statsUiModel.color.copy(alpha = 0.1f),
                shape = shape,
            )
            .padding(
                horizontal = TileHorizontalPadding,
                vertical = TileVerticalPadding,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stat.value.toString(),
            style = PokedexTheme.typography.titleSmall,
            color = stat.statsUiModel.color,
            maxLines = 1,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StatBarHeight)
                    .background(
                        color = stat.statsUiModel.color.copy(alpha = 0.18f),
                        shape = RoundedCornerShape(99.dp),
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(stat.normalizedPercent)
                        .height(StatBarHeight)
                        .background(
                            color = stat.statsUiModel.color,
                            shape = RoundedCornerShape(99.dp),
                        )
                )
            }

            Spacer(modifier = Modifier.height(LabelTopPadding))

            Text(
                text = label,
                style = PokedexTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = PokedexTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        PokedexTheme.colors.background
                    )
                    .padding(
                        horizontal = LabelHorizontalPadding,
                        vertical = LabelVerticalPadding,
                    ),
            )
        }
    }
}

private fun calculateColumnCount(
    statsCount: Int,
    availableWidth: Int,
    wideGridMinWidth: Int,
): Int {
    if (statsCount == 0 || availableWidth <= 0) return 0

    val preferredColumns = if (availableWidth >= wideGridMinWidth) {
        WideColumnCount
    } else {
        CompactColumnCount
    }

    return min(statsCount, preferredColumns).coerceAtLeast(1)
}

@Preview
@Composable
private fun PokemonStatsCardPreview() {
    var isOpen by remember { mutableStateOf(false) }

    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PokemonStatsCard(
                stats = rememberPreviewStats(),
                isOpen = isOpen,
                pokemonType = PokemonTypeUiModel.Grass,
                onClick = { isOpen = !isOpen },
            )
        }
    }
}

@Preview
@Composable
private fun PokemonStatsCardOpenPreview() {
    var isOpen by remember { mutableStateOf(true) }

    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PokemonStatsCard(
                stats = rememberPreviewStats(),
                isOpen = isOpen,
                pokemonType = PokemonTypeUiModel.Grass,
                onClick = { isOpen = !isOpen },
            )
        }
    }
}

@Composable
private fun rememberPreviewStats(): List<PokemonStatValueUiModel> {
    return remember {
        PokemonStatsUiModel.entries
            .filterNot { it == PokemonStatsUiModel.Unknown }
            .map {
                PokemonStatValueUiModel(
                    statsUiModel = it,
                    value = it.previewValueRange.random(),
                    minValue = it.previewValueRange.first,
                    maxValue = it.previewValueRange.last,
                )
            }
    }
}

private val DefaultCardWidth = 360.dp
private val CardContentPadding = 12.dp
private val TileGap = 8.dp
private val TileHeight = 68.dp
private val TileHorizontalPadding = 8.dp
private val TileVerticalPadding = 8.dp
private val StatBarHeight = 5.dp
private val LabelTopPadding = 5.dp
private val LabelHorizontalPadding = 6.dp
private val LabelVerticalPadding = 1.dp
private val RadarTopPadding = 12.dp
private val WideGridMinWidth = 280.dp

private const val CompactColumnCount = 2
private const val WideColumnCount = 3
