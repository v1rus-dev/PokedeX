package yegor.cheprasov.pokedex.core.design.composable.pull_to_refresh

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_loading
import pokedex.core.resources.generated.resources.ic_pokeball_loader
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import kotlin.math.max

typealias PokedexPullToRefreshState = PullToRefreshState

private val PullToRefreshHeaderHeight = 56.dp
private val PullToRefreshIconSize = 24.dp
private val InfiniteLoaderSize = 36.dp
private val InfiniteLoaderStroke = 3.dp
private const val PendingRefreshHoldMillis = 220L
private val ProgressTrackHeight = 4.dp
private val ProgressIndicatorHeight = 28.dp
private val ProgressIconSize = 18.dp

sealed interface PokedexPullToRefreshIndicator {
    data class Progress(val progressPercent: Int) : PokedexPullToRefreshIndicator

    data object Infinity : PokedexPullToRefreshIndicator
}

@Composable
fun rememberPokedexPullToRefreshState(): PokedexPullToRefreshState = rememberPullToRefreshState()

@Composable
fun PokedexPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    state: PokedexPullToRefreshState,
    modifier: Modifier = Modifier,
    indicator: PokedexPullToRefreshIndicator = PokedexPullToRefreshIndicator.Infinity,
    content: @Composable BoxScope.() -> Unit,
) {
    var isRefreshPending by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            isRefreshPending = false
        }
    }

    LaunchedEffect(isRefreshPending, isRefreshing) {
        if (isRefreshPending && !isRefreshing) {
            delay(PendingRefreshHoldMillis)
            if (!isRefreshing) {
                isRefreshPending = false
            }
        }
    }

    val shouldKeepHeaderExpanded = isRefreshing || isRefreshPending
    val headerHeight by animateDpAsState(
        targetValue = if (shouldKeepHeaderExpanded) {
            PullToRefreshHeaderHeight
        } else {
            PullToRefreshHeaderHeight * state.distanceFraction.coerceIn(0f, 1f)
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "pokedexPullToRefreshHeaderHeight"
    )
    val headerDistanceFraction = if (shouldKeepHeaderExpanded) {
        1f
    } else {
        state.distanceFraction
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshPending = true
            onRefresh()
        },
        state = state,
        indicator = {}
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .clipToBounds()
            ) {
                PullToRefreshHeader(
                    indicator = indicator,
                    isRefreshing = isRefreshing,
                    distanceFraction = headerDistanceFraction,
                    modifier = Modifier
                        .height(PullToRefreshHeaderHeight)
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = PokedexTheme.spacing.large)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun PullToRefreshHeader(
    indicator: PokedexPullToRefreshIndicator,
    isRefreshing: Boolean,
    distanceFraction: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isRefreshing,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            contentAlignment = Alignment.Center,
            label = "pokedexPullToRefreshHeaderContent"
        ) { refreshing ->
            when {
                refreshing && indicator is PokedexPullToRefreshIndicator.Progress -> {
                    ProgressPullToRefreshIndicator(
                        progressPercent = indicator.progressPercent,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                indicator is PokedexPullToRefreshIndicator.Infinity -> {
                    InfinityPullToRefreshIndicator(
                        isRefreshing = refreshing,
                        distanceFraction = distanceFraction,
                    )
                }

                else -> {
                    PullToRefreshIdleIndicator(
                        distanceFraction = distanceFraction,
                    )
                }
            }
        }
    }
}

@Composable
private fun PullToRefreshIdleIndicator(
    distanceFraction: Float,
    modifier: Modifier = Modifier,
) {
    val progress = distanceFraction.coerceIn(0f, 1f)
    val travelOffset = PokedexTheme.spacing.medium * (1f - progress)

    Image(
        painter = painterResource(Res.drawable.ic_loading),
        contentDescription = null,
        modifier = modifier
            .offset(y = travelOffset)
            .size(PullToRefreshIconSize)
            .graphicsLayer {
                rotationZ = progress * 360f
                alpha = progress
                scaleX = 0.84f + (0.16f * progress)
                scaleY = 0.84f + (0.16f * progress)
            }
    )
}

@Composable
private fun InfinityPullToRefreshIndicator(
    isRefreshing: Boolean,
    distanceFraction: Float,
    modifier: Modifier = Modifier,
) {
    val progress = distanceFraction.coerceIn(0f, 1f)
    val infiniteTransition = rememberInfiniteTransition(label = "infinityPullToRefresh")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = LinearEasing,
            )
        ),
        label = "infinityPullToRefreshRotation"
    )
    val arcSweep by animateFloatAsState(
        targetValue = if (isRefreshing) 110f else max(24f, 300f * progress),
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "infinityPullToRefreshSweep"
    )
    val indicatorRotation = if (isRefreshing) rotation else progress * 300f
    val trackColor = PokedexTheme.colors.surfaceBorder.copy(alpha = 0.35f)
    val accentColor = PokedexTheme.colors.loadingFillColor

    Box(
        modifier = modifier.size(InfiniteLoaderSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = indicatorRotation
                    alpha = 0.5f + (0.5f * progress)
                }
        ) {
            val strokeWidth = InfiniteLoaderStroke.toPx()
            val arcSize = size.minDimension - strokeWidth
            val topLeft = Offset(
                x = (size.width - arcSize) / 2f,
                y = (size.height - arcSize) / 2f,
            )

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                color = accentColor,
                startAngle = -90f,
                sweepAngle = arcSweep,
                useCenter = false,
                topLeft = topLeft,
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Image(
            painter = painterResource(Res.drawable.ic_loading),
            contentDescription = null,
            modifier = Modifier
                .size(PullToRefreshIconSize)
                .graphicsLayer {
                    rotationZ = indicatorRotation
                    alpha = 0.7f + (0.3f * progress)
                }
        )
    }
}

@Composable
private fun ProgressPullToRefreshIndicator(
    progressPercent: Int,
    modifier: Modifier = Modifier,
) {
    val progress by animateFloatAsState(
        targetValue = (progressPercent / 100f).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 360, easing = FastOutSlowInEasing),
        label = "progressPullToRefreshProgress"
    )
    val infiniteTransition = rememberInfiniteTransition(label = "progressPullToRefreshShimmer")
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing,
            )
        ),
        label = "progressPullToRefreshShimmerProgress"
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
private fun PokedexPullToRefreshProgressPreview() {
    PokedexTheme {
        PokedexPullToRefresh(
            isRefreshing = true,
            onRefresh = {},
            state = rememberPokedexPullToRefreshState(),
            indicator = PokedexPullToRefreshIndicator.Progress(progressPercent = 64),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview
@Composable
private fun PokedexPullToRefreshInfinityPreview() {
    PokedexTheme {
        PokedexPullToRefresh(
            isRefreshing = true,
            onRefresh = {},
            state = rememberPokedexPullToRefreshState(),
            indicator = PokedexPullToRefreshIndicator.Infinity,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}
