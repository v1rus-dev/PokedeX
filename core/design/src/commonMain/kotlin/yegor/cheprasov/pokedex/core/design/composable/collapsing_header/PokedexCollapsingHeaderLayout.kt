package yegor.cheprasov.pokedex.core.design.composable.collapsing_header

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import kotlin.math.max

@Stable
class CollapsingHeaderLayoutState internal constructor(
    initialOverlapFraction: Float
) {
    private val initialOverlapFraction = initialOverlapFraction.coerceIn(0f, 1f)
    private val offsetAnimation = Animatable(0f)
    private var hasAppliedInitialFraction = false

    internal var offsetPx by mutableFloatStateOf(0f)
        private set

    internal var collapseRangePx by mutableFloatStateOf(0f)
        private set

    val overlapFraction: Float
        get() = if (collapseRangePx == 0f) {
            0f
        } else {
            (-offsetPx / collapseRangePx).coerceIn(0f, 1f)
        }

    internal suspend fun updateCollapseRange(collapseRangePx: Float) {
        val newCollapseRangePx = max(0f, collapseRangePx)

        this.collapseRangePx = newCollapseRangePx
        offsetAnimation.updateBounds(
            lowerBound = -newCollapseRangePx,
            upperBound = 0f,
        )

        val targetOffsetPx = when {
            newCollapseRangePx == 0f -> 0f
            !hasAppliedInitialFraction -> -newCollapseRangePx * initialOverlapFraction
            else -> offsetPx.coerceIn(-newCollapseRangePx, 0f)
        }

        hasAppliedInitialFraction = true
        snapToOffset(targetOffsetPx)
    }

    internal fun dragBy(deltaPx: Float): Float {
        if (collapseRangePx == 0f) {
            return 0f
        }

        val newOffsetPx = (offsetPx + deltaPx).coerceIn(-collapseRangePx, 0f)
        val consumedPx = newOffsetPx - offsetPx
        offsetPx = newOffsetPx
        return consumedPx
    }

    suspend fun expand() {
        animateToOverlapFraction(0f)
    }

    suspend fun collapse() {
        animateToOverlapFraction(1f)
    }

    suspend fun animateToOverlapFraction(fraction: Float) {
        if (collapseRangePx == 0f) {
            snapToOffset(0f)
            return
        }

        val targetOffsetPx = -collapseRangePx * fraction.coerceIn(0f, 1f)

        offsetAnimation.stop()
        offsetAnimation.updateBounds(
            lowerBound = -collapseRangePx,
            upperBound = 0f,
        )
        offsetAnimation.snapTo(offsetPx.coerceIn(-collapseRangePx, 0f))
        offsetAnimation.animateTo(
            targetValue = targetOffsetPx,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium,
                visibilityThreshold = CollapseOffsetVisibilityThresholdPx,
            ),
        ) {
            offsetPx = value
        }
        snapToOffset(targetOffsetPx)
    }

    suspend fun snapToOverlapFraction(fraction: Float) {
        if (collapseRangePx == 0f) {
            snapToOffset(0f)
            return
        }

        snapToOffset(-collapseRangePx * fraction.coerceIn(0f, 1f))
    }

    private suspend fun snapToOffset(targetOffsetPx: Float) {
        val coercedOffsetPx = if (collapseRangePx == 0f) {
            0f
        } else {
            targetOffsetPx.coerceIn(-collapseRangePx, 0f)
        }

        offsetAnimation.stop()
        offsetAnimation.snapTo(coercedOffsetPx)
        offsetPx = coercedOffsetPx
    }

    internal val isPartiallyCollapsed: Boolean
        get() = overlapFraction > 0f && overlapFraction < 1f
}

@Composable
fun rememberCollapsingHeaderLayoutState(
    initialOverlapFraction: Float = 0f
): CollapsingHeaderLayoutState = remember(initialOverlapFraction) {
    CollapsingHeaderLayoutState(initialOverlapFraction)
}

@Stable
interface CollapsingHeaderFlingBehavior {
    suspend fun performFling(
        state: CollapsingHeaderLayoutState,
        initialVelocity: Float
    ): Boolean
}

@Composable
fun rememberVelocityBasedCollapsingHeaderFlingBehavior(
    settleThreshold: Float = 0.5f,
    velocityThreshold: Float = DefaultFlingVelocityThresholdPx
): CollapsingHeaderFlingBehavior = remember(settleThreshold, velocityThreshold) {
    VelocityBasedCollapsingHeaderFlingBehavior(
        settleThreshold = settleThreshold,
        velocityThreshold = velocityThreshold,
    )
}

@Composable
fun rememberSnapCollapsingHeaderFlingBehavior(
    settleThreshold: Float = 0.5f
): CollapsingHeaderFlingBehavior = remember(settleThreshold) {
    SnapCollapsingHeaderFlingBehavior(settleThreshold)
}

@Composable
fun rememberNoCollapsingHeaderFlingBehavior(): CollapsingHeaderFlingBehavior = remember {
    NoCollapsingHeaderFlingBehavior
}

@Composable
fun CollapsingHeaderLayout(
    modifier: Modifier = Modifier,
    headerHeight: Dp = 0.dp,
    state: CollapsingHeaderLayoutState = rememberCollapsingHeaderLayoutState(),
    minHeaderHeight: Dp = 0.dp,
    initialContentOverlap: Dp = 0.dp,
    topInset: Dp = with(LocalDensity.current) { WindowInsets.statusBars.getTop(this).toDp() },
    backgroundColor: Color = PokedexTheme.colors.background,
    cornerRadius: Shape = RoundedCornerShape(PokedexTheme.radii.large),
    settleThreshold: Float = 0.5f,
    flingBehavior: CollapsingHeaderFlingBehavior = rememberVelocityBasedCollapsingHeaderFlingBehavior(settleThreshold),
    header: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val density = LocalDensity.current
    val headerHeightPx = with(density) { headerHeight.toPx() }
    val minHeaderHeightPx = with(density) { minHeaderHeight.toPx() }
    val initialContentOverlapPx = with(density) { initialContentOverlap.toPx() }
    val topInsetPx = with(density) { topInset.toPx() }

    val expandedContentTopPx = topInsetPx + max(
        a = headerHeightPx - initialContentOverlapPx,
        b = minHeaderHeightPx,
    )
    val collapsedContentTopPx = topInsetPx + minHeaderHeightPx
    val collapseRangePx = max(0f, expandedContentTopPx - collapsedContentTopPx)

    LaunchedEffect(state, collapseRangePx) {
        state.updateCollapseRange(collapseRangePx)
    }

    val contentTop = with(density) {
        (expandedContentTopPx + state.offsetPx).toDp()
    }
    val contentElevation = if (state.overlapFraction >= 1f) {
        CollapsedContentElevation
    } else {
        0.dp
    }

    val nestedScrollConnection = remember(state, flingBehavior) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y >= 0f) {
                    return Offset.Zero
                }

                val consumedY = state.dragBy(available.y)
                return Offset(x = 0f, y = consumedY)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (available.y <= 0f) {
                    return Offset.Zero
                }

                val consumedY = state.dragBy(available.y)
                return Offset(x = 0f, y = consumedY)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val consumed = flingBehavior.performFling(
                    state = state,
                    initialVelocity = available.y,
                )
                return if (consumed) available else Velocity.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val consumedFling = flingBehavior.performFling(
                    state = state,
                    initialVelocity = available.y,
                )
                return if (consumedFling) available else Velocity.Zero
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight),
            content = header,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentTop)
                .shadow(
                    elevation = contentElevation,
                    shape = cornerRadius,
                    clip = false,
                )
                .clip(cornerRadius)
                .background(backgroundColor, cornerRadius),
            content = content,
        )
    }
}

@Preview
@Composable
private fun CollapsingHeaderLayoutPreview() {
    PokedexTheme {
        CollapsingHeaderLayout(
            headerHeight = 160.dp,
            initialContentOverlap = 32.dp,
            header = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PokedexTheme.colors.primary)
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(PokedexTheme.spacing.large)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                            .background(PokedexTheme.colors.surface)
                    )
                }
            },
        )
    }
}

private class VelocityBasedCollapsingHeaderFlingBehavior(
    private val settleThreshold: Float,
    private val velocityThreshold: Float,
) : CollapsingHeaderFlingBehavior {
    override suspend fun performFling(
        state: CollapsingHeaderLayoutState,
        initialVelocity: Float
    ): Boolean {
        if (!state.isPartiallyCollapsed) {
            return false
        }

        val targetFraction = when {
            initialVelocity <= -velocityThreshold -> 1f
            initialVelocity >= velocityThreshold -> 0f
            state.overlapFraction >= settleThreshold.coerceIn(0f, 1f) -> 1f
            else -> 0f
        }
        state.animateToOverlapFraction(targetFraction)
        return true
    }
}

private class SnapCollapsingHeaderFlingBehavior(
    private val settleThreshold: Float
) : CollapsingHeaderFlingBehavior {
    override suspend fun performFling(
        state: CollapsingHeaderLayoutState,
        initialVelocity: Float
    ): Boolean {
        if (!state.isPartiallyCollapsed) {
            return false
        }

        val targetFraction = if (state.overlapFraction >= settleThreshold.coerceIn(0f, 1f)) {
            1f
        } else {
            0f
        }
        state.animateToOverlapFraction(targetFraction)
        return true
    }
}

private data object NoCollapsingHeaderFlingBehavior : CollapsingHeaderFlingBehavior {
    override suspend fun performFling(
        state: CollapsingHeaderLayoutState,
        initialVelocity: Float
    ): Boolean = false
}

private val CollapsedContentElevation = 5.dp
private const val DefaultFlingVelocityThresholdPx = 1_000f
private const val CollapseOffsetVisibilityThresholdPx = 0.5f
