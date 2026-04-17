package yegor.cheprasov.pokedex.features.root.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.NavKey

@Stable
class RootNavigator internal constructor(
    private val routesById: Map<String, NavKey>,
    private val selectedRouteIdState: MutableState<String>,
) {
    val selectedRoute: NavKey
        get() = routesById.getValue(selectedRouteIdState.value)

    fun selectRoute(route: NavKey) {
        selectedRouteIdState.value = route.routeId()
    }
}

@Composable
fun rememberRootNavigator(
    startRoute: NavKey,
    topLevelRoutes: List<NavKey>,
): RootNavigator {
    val routesById = remember(topLevelRoutes) {
        topLevelRoutes.associateBy(NavKey::routeId)
    }
    val fallbackRouteId = remember(startRoute) {
        startRoute.routeId()
    }
    val selectedRouteIdState = rememberSaveable {
        mutableStateOf(fallbackRouteId)
    }

    if (selectedRouteIdState.value !in routesById) {
        selectedRouteIdState.value = fallbackRouteId
    }

    return remember(routesById, selectedRouteIdState) {
        RootNavigator(
            routesById = routesById,
            selectedRouteIdState = selectedRouteIdState,
        )
    }
}

private fun NavKey.routeId(): String = toString()
