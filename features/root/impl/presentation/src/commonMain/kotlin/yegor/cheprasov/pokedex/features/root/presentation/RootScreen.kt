package yegor.cheprasov.pokedex.features.root.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.root.presentation.navigation.rememberRootNavigator

@Composable
fun RootScreen(
    navigator: AppNavigator,
    topLevelDestinations: List<TopLevelDestinationSpec>,
) {
    val rootNavigator = rememberRootNavigator(
        startRoute = topLevelDestinations.first().route,
        topLevelRoutes = topLevelDestinations.map(TopLevelDestinationSpec::route),
    )
    val currentDestination = topLevelDestinations.first { it.route == rootNavigator.selectedRoute }

    Scaffold(
        bottomBar = {
            NavigationBar {
                topLevelDestinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination.route == destination.route,
                        onClick = {
                            rootNavigator.selectRoute(destination.route)
                        },
                        icon = {
                            Image(
                                painter = painterResource(destination.icon),
                                modifier = Modifier.size(24.dp),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(destination.label)
                            )
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            currentDestination.content(navigator)
        }
    }
}
