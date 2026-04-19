package yegor.cheprasov.pokedex.features.root.presentation

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec

@Serializable
data object RootTabs : NavKey

val rootTabsNavigationSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(RootTabs::class)
    }
}

fun rootEntryProvider(
    navigator: AppNavigator,
    topLevelDestinations: List<TopLevelDestinationSpec>,
): (NavKey) -> NavEntry<NavKey> {
    return entryProvider {
        entry<RootTabs> {
            RootDestination(
                navigator = navigator,
                topLevelDestinations = topLevelDestinations,
            )
        }
    }
}
