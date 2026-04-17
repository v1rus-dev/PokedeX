package yegor.cheprasov.pokedex.core.design.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.serialization.SavedStateConfiguration
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

class AppNavigationState internal constructor(
    val backStack: NavBackStack<NavKey>,
) {
    @OptIn(KoinExperimentalAPI::class)
    @Composable
    fun toEntries(
        entryProvider: (NavKey) -> NavEntry<NavKey> = koinEntryProvider(),
    ): List<NavEntry<NavKey>> {
        return rememberDecoratedNavEntries(
            backStack = backStack,
            entryDecorators = rememberNavigationEntryDecorators(),
            entryProvider = entryProvider,
        )
    }
}

@Composable
fun rememberAppNavigationState(
    startRoute: NavKey,
    configuration: SavedStateConfiguration,
): AppNavigationState {
    val backStack = rememberNavBackStack(configuration, startRoute)

    return remember(backStack) {
        AppNavigationState(
            backStack = backStack,
        )
    }
}

@Composable
private fun rememberNavigationEntryDecorators(): List<NavEntryDecorator<NavKey>> {
    return listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator(),
    )
}
