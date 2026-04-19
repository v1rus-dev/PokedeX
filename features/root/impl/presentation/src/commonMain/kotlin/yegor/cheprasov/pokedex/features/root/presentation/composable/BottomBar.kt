package yegor.cheprasov.pokedex.features.root.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.favorites
import pokedex.core.resources.generated.resources.home
import pokedex.core.resources.generated.resources.pokeball
import pokedex.core.resources.generated.resources.pokeball_settings
import pokedex.core.resources.generated.resources.settings
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.favorites.api.Favorites
import yegor.cheprasov.pokedex.features.home.api.Home
import yegor.cheprasov.pokedex.features.settings.api.Settings

@Composable
internal fun BottomBar(
    topLevelDestinations: List<TopLevelDestinationSpec>,
    currentDestination: TopLevelDestinationSpec,
    modifier: Modifier = Modifier,
    onClick: (NavKey) -> Unit,
) {
    val shape = RoundedCornerShape(24.dp)
    val selectedIndex = topLevelDestinations.indexOf(currentDestination)
    val primaryColor = MaterialTheme.colorScheme.primary
    val buttonCenters = remember { mutableStateMapOf<Int, Float>() }
    val dotX = remember { Animatable(0f) }
    val dotWidth = remember { Animatable(6f) }
    var isInitialized by remember { mutableStateOf(false) }
    var boxRootX by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(selectedIndex, buttonCenters.size) {
        buttonCenters[selectedIndex]?.let { targetX ->
            if (!isInitialized) {
                dotX.snapTo(targetX)
                isInitialized = true
                return@LaunchedEffect
            }
            launch {
                dotWidth.animateTo(18f, tween(150, easing = FastOutSlowInEasing))
                dotWidth.animateTo(6f, tween(150, easing = FastOutSlowInEasing))
            }
            launch {
                dotX.animateTo(targetX, tween(300, easing = FastOutSlowInEasing))
            }
        }
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { coords ->
                boxRootX = coords.positionInRoot().x
            }
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f), shape)
            .border(0.5.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), shape)
            .height(64.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            topLevelDestinations.forEachIndexed { index, spec ->
                BottomBarButton(
                    spec = spec,
                    isSelected = currentDestination == spec,
                    onPositioned = { centerXInRoot ->
                        buttonCenters[index] = centerXInRoot - boxRootX
                    },
                    onClick = onClick,
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            if (buttonCenters.isNotEmpty()) {
                val height = 4.dp.toPx()
                val width = dotWidth.value.dp.toPx()
                val radius = height / 2f
                val cy = size.height - 8.dp.toPx()

                drawRoundRect(
                    color = primaryColor,
                    topLeft = Offset(x = dotX.value - width / 2f, y = cy - radius),
                    size = Size(width, height),
                    cornerRadius = CornerRadius(radius),
                )
            }
        }
    }
}

@Composable
private fun BottomBarButton(
    spec: TopLevelDestinationSpec,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onPositioned: (centerXInRoot: Float) -> Unit,
    onClick: (NavKey) -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.4f,
        animationSpec = tween(200),
        label = "alpha",
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale",
    )

    Box(
        modifier = modifier
            .size(56.dp)
            .onGloballyPositioned { coords ->
                onPositioned(coords.positionInRoot().x + coords.size.width / 2f)
            }
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, radius = 24.dp),
                onClick = { onClick(spec.route) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(spec.icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer {
                    this.alpha = alpha
                    scaleX = scale
                    scaleY = scale
                },
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    val home = TopLevelDestinationSpec(
        route = Home,
        icon = Res.drawable.pokeball,
        label = Res.string.home,
        content = {},
    )
    val favorites = TopLevelDestinationSpec(
        route = Favorites,
        icon = Res.drawable.favorites,
        label = Res.string.favorites,
        content = {},
    )
    val settings = TopLevelDestinationSpec(
        route = Settings,
        icon = Res.drawable.pokeball_settings,
        label = Res.string.settings,
        content = {},
    )
    var currentDestination by remember { mutableStateOf(home) }

    PokedexTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue.copy(alpha = 0.1f)),
        ) {
            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                topLevelDestinations = listOf(
                    home,
                    favorites,
                    settings,
                ),
                currentDestination = currentDestination,
            ) {
                when (it) {
                    Home -> currentDestination = home
                    Favorites -> currentDestination = favorites
                    Settings -> currentDestination = settings
                }
            }
        }
    }
}
