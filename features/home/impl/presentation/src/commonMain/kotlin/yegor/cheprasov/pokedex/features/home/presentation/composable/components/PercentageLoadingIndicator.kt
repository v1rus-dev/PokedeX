package yegor.cheprasov.pokedex.features.home.presentation.composable.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_pokeball_loader
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import kotlin.math.max

private val ProgressTrackHeight = 4.dp
private val ProgressIndicatorHeight = 28.dp
private val ProgressIconSize = 18.dp

@Composable
internal fun PercentageLoadingIndicator(modifier: Modifier = Modifier, progressPercent: Int) {
    val progress by animateFloatAsState(
        targetValue = (progressPercent / 100f).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 360, easing = FastOutSlowInEasing),
        label = "percentageLoadingProgress"
    )
    val infiniteTransition = rememberInfiniteTransition(label = "percentageLoadingShimmer")
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1100, easing = LinearEasing)),
        label = "percentageLoadingShimmerProgress"
    )

    val surfaceBorder = PokedexTheme.colors.surfaceBorder
    val loadingFillColor = PokedexTheme.colors.loadingFillColor

    BoxWithConstraints(
        modifier = modifier.height(ProgressIndicatorHeight),
        contentAlignment = Alignment.CenterStart
    ) {
        val iconOffset = (maxWidth * progress) - (ProgressIconSize / 2)

        Box(modifier = Modifier.fillMaxWidth()) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ProgressIndicatorHeight)
            ) {
                val trackHeight = ProgressTrackHeight.toPx()
                val top = (size.height - trackHeight) / 2f
                val cornerRadius = CornerRadius(x = trackHeight / 2f, y = trackHeight / 2f)
                val fillWidth = size.width * progress

                drawRoundRect(
                    color = surfaceBorder.copy(alpha = 0.45f),
                    topLeft = Offset(x = 0f, y = top),
                    size = Size(width = size.width, height = trackHeight),
                    cornerRadius = cornerRadius
                )

                if (fillWidth > 0f) {
                    val shimmerWidth = max(fillWidth * 0.4f, trackHeight * 8f)
                    val shimmerStart = ((fillWidth + shimmerWidth) * shimmerProgress) - shimmerWidth
                    val shimmerEnd = shimmerStart + shimmerWidth

                    drawRoundRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                loadingFillColor.copy(alpha = 0.78f),
                                loadingFillColor,
                                Color.White.copy(alpha = 0.85f),
                                loadingFillColor
                            ),
                            startX = shimmerStart,
                            endX = shimmerEnd
                        ),
                        topLeft = Offset(x = 0f, y = top),
                        size = Size(width = fillWidth, height = trackHeight),
                        cornerRadius = cornerRadius
                    )
                }
            }

            Image(
                painter = painterResource(Res.drawable.ic_pokeball_loader),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = iconOffset)
                    .size(ProgressIconSize)
            )
        }
    }
}

@Preview
@Composable
private fun PercentageLoadingIndicatorPreview() {
    PokedexTheme {
        PercentageLoadingIndicator(
            modifier = Modifier.fillMaxWidth(),
            progressPercent = 64
        )
    }
}
