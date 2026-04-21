package yegor.cheprasov.pokedex.features.root.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.common.platform.currentPlatform
import yegor.cheprasov.pokedex.core.common.platform.isAndroid
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.root.presentation.composable.BottomBar
import yegor.cheprasov.pokedex.features.root.presentation.navigation.rememberRootNavigator

@Composable
fun RootScreen(
    topLevelDestinations: List<TopLevelDestinationSpec>,
) {
    val rootNavigator = rememberRootNavigator(
        startRoute = topLevelDestinations.first().route,
        topLevelRoutes = topLevelDestinations.map(TopLevelDestinationSpec::route),
    )
    val currentDestination = topLevelDestinations.first { it.route == rootNavigator.selectedRoute }

    Scaffold(
        containerColor = Color.Blue,
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            currentDestination.content()

            BottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = if (currentPlatform.isAndroid) 16.dp else 0.dp)
                    .safeDrawingPadding()
                    .fillMaxWidth(),
                topLevelDestinations = topLevelDestinations, currentDestination = currentDestination
            ) {
                if (currentDestination.route != it) {
                    rootNavigator.selectRoute(it)
                }
            }
        }
    }
}
