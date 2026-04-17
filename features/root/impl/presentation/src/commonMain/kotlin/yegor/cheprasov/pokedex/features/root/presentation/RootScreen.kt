package yegor.cheprasov.pokedex.features.root.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                            Text(text = destination.iconLabel)
                        },
                        label = {
                            Text(text = destination.label)
                        },
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
