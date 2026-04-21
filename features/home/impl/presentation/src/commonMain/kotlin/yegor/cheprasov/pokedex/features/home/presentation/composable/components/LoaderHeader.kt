package yegor.cheprasov.pokedex.features.home.presentation.composable.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_loading
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

private val LoaderHeaderHeight = 72.dp
private val LoaderIconSize = 24.dp

@Composable
internal fun LoaderHeader(
    modifier: Modifier = Modifier,
    distanceFraction: Float = 0f,
    loadingPercent: Int? = null
) {
    val isLoading = loadingPercent != null

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(LoaderHeaderHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = isLoading,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            contentAlignment = Alignment.Center,
            label = "loaderHeaderContent"
        ) { shouldShowLoading ->
            if (shouldShowLoading) {
                PercentageLoadingIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    progressPercent = loadingPercent ?: 0
                )
            } else {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    PullToRefreshIdleIndicator(
                        distanceFraction = distanceFraction
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoaderHeaderPreview() {
    PokedexTheme {
        LoaderHeader(distanceFraction = 1f)
    }
}

@Composable
private fun PullToRefreshIdleIndicator(modifier: Modifier = Modifier, distanceFraction: Float) {
    val progress = distanceFraction.coerceIn(0f, 1f)
    val travelOffset = PokedexTheme.spacing.medium * (1f - progress)

    Image(
        painter = painterResource(Res.drawable.ic_loading),
        contentDescription = null,
        modifier = modifier
            .offset(y = travelOffset)
            .size(LoaderIconSize)
            .graphicsLayer {
                rotationZ = progress * 360f
                alpha = progress
                scaleX = 0.84f + (0.16f * progress)
                scaleY = 0.84f + (0.16f * progress)
            }
    )
}
