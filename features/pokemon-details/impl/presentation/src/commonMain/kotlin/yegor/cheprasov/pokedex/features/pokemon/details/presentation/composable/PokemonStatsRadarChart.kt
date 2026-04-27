package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import org.jetbrains.compose.resources.stringResource
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatValueUiModel

@Composable
internal fun PokemonStatsRadarChart(
    stats: List<PokemonStatValueUiModel>,
    chartColor: Color,
    modifier: Modifier = Modifier,
) {
    val labelColor = PokedexTheme.colors.textSecondary

    Layout(
        modifier = modifier,
        content = {
            Canvas(modifier = Modifier) {
                if (stats.isEmpty()) return@Canvas

                val center = Offset(x = size.width / 2f, y = size.height / 2f)
                val radius = min(size.width, size.height) * RadarRadiusFraction
                val axisCount = stats.size
                val step = FullCircleRadians / axisCount

                repeat(RadarGridLevels) { levelIndex ->
                    val levelRadius = radius * (levelIndex + 1) / RadarGridLevels
                    val path = buildRadarPath(
                        axisCount = axisCount,
                        step = step,
                        center = center,
                        radiusProvider = { levelRadius },
                    )

                    drawPath(
                        path = path,
                        color = chartColor.copy(alpha = 0.04f),
                        style = Stroke(width = 1.dp.toPx()),
                    )
                }

                val valuePath = buildRadarPath(
                    axisCount = axisCount,
                    step = step,
                    center = center,
                    radiusProvider = { index ->
                        radius * stats[index].normalizedPercent
                    },
                )

                drawPath(
                    path = valuePath,
                    brush = Brush.radialGradient(
                        colors = listOf(
                            chartColor.copy(alpha = RadarFillCenterAlpha),
                            chartColor.copy(alpha = RadarFillEdgeAlpha),
                        ),
                        center = center,
                        radius = radius,
                    ),
                )
                drawPath(
                    path = valuePath,
                    color = chartColor.copy(alpha = RadarStrokeAlpha),
                    style = Stroke(width = 0.5.dp.toPx()),
                )
            }

            stats.forEach { stat ->
                Text(
                    text = stringResource(stat.statsUiModel.label),
                    style = PokedexTheme.typography.labelSmall,
                    color = labelColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    ) { measurables, constraints ->
        val layoutSize = constraints.constrainWidth(RadarChartLayoutSize.roundToPx())
        val graphSize = min(layoutSize, RadarGraphSize.roundToPx())
        val canvasPlaceable = measurables.first().measure(
            Constraints.fixed(width = graphSize, height = graphSize)
        )
        val labelPlaceables = measurables.drop(1).map { measurable ->
            measurable.measure(
                Constraints(
                    minWidth = 0,
                    maxWidth = RadarLabelMaxWidth.roundToPx(),
                    minHeight = 0,
                    maxHeight = Constraints.Infinity,
                )
            )
        }
        val center = layoutSize / 2
        val labelRadius = (graphSize / 2f + RadarLabelOffset.roundToPx()).coerceAtMost(layoutSize / 2f)
        val step = if (labelPlaceables.isEmpty()) {
            0.0
        } else {
            FullCircleRadians / labelPlaceables.size
        }

        layout(width = layoutSize, height = layoutSize) {
            canvasPlaceable.placeRelative(
                x = center - canvasPlaceable.width / 2,
                y = center - canvasPlaceable.height / 2,
            )

            labelPlaceables.forEachIndexed { index, placeable ->
                val labelPoint = radarPoint(
                    index = index,
                    step = step,
                    center = Offset(center.toFloat(), center.toFloat()),
                    radius = labelRadius,
                )

                placeable.placeRelative(
                    x = (labelPoint.x - placeable.width / 2f).roundToInt(),
                    y = (labelPoint.y - placeable.height / 2f).roundToInt(),
                )
            }
        }
    }
}

private fun buildRadarPath(
    axisCount: Int,
    step: Double,
    center: Offset,
    radiusProvider: (Int) -> Float,
): Path {
    val path = Path()

    repeat(axisCount) { index ->
        val point = radarPoint(
            index = index,
            step = step,
            center = center,
            radius = radiusProvider(index),
        )

        if (index == 0) {
            path.moveTo(x = point.x, y = point.y)
        } else {
            path.lineTo(x = point.x, y = point.y)
        }
    }

    path.close()
    return path
}

private fun radarPoint(
    index: Int,
    step: Double,
    center: Offset,
    radius: Float,
): Offset {
    val angle = RadarStartAngleRadians + step * index

    return Offset(
        x = center.x + cos(angle).toFloat() * radius,
        y = center.y + sin(angle).toFloat() * radius,
    )
}

private val RadarChartLayoutSize = 244.dp
private val RadarGraphSize = 176.dp
private val RadarLabelOffset = 20.dp
private val RadarLabelMaxWidth = 86.dp

private const val RadarGridLevels = 4
private const val RadarRadiusFraction = 0.46f
private const val RadarFillCenterAlpha = 0.02f
private const val RadarFillEdgeAlpha = 0.32f
private const val RadarStrokeAlpha = 0.04f
private val FullCircleRadians = PI * 2
private val RadarStartAngleRadians = -PI / 2
