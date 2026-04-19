package yegor.cheprasov.pokedex.features.root.presentation

import androidx.compose.runtime.Composable
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec

@Composable
fun RootDestination(
    navigator: AppNavigator,
    topLevelDestinations: List<TopLevelDestinationSpec>,
) {
    RootScreen(topLevelDestinations = topLevelDestinations)
}