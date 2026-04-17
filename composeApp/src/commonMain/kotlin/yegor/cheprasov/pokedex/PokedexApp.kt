package yegor.cheprasov.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.navigationConfiguration
import yegor.cheprasov.pokedex.core.design.navigation.rememberAppNavigationState
import yegor.cheprasov.pokedex.di.appModules
import yegor.cheprasov.pokedex.features.favorites.presentation.favoritesTopLevelDestination
import yegor.cheprasov.pokedex.features.pokemon.details.api.pokemonDetailsSerializersModule
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.pokemonListTopLevelDestination
import yegor.cheprasov.pokedex.features.root.presentation.RootTabs
import yegor.cheprasov.pokedex.features.root.presentation.rootEntryProvider
import yegor.cheprasov.pokedex.features.root.presentation.rootTabsNavigationSerializersModule

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun PokedexApp() {
    MaterialTheme {
        val topLevelDestinations = remember {
            listOf(
                pokemonListTopLevelDestination,
                favoritesTopLevelDestination,
            )
        }
        val navigator: AppNavigator = koinInject()
        val rootOnlyEntryProvider = remember(navigator, topLevelDestinations) {
            rootEntryProvider(
                navigator = navigator,
                topLevelDestinations = topLevelDestinations,
            )
        }
        val navigationState = rememberAppNavigationState(
            startRoute = RootTabs,
            configuration = navigationConfiguration(
                listOf(
                    rootTabsNavigationSerializersModule,
                    pokemonDetailsSerializersModule,
                ),
            ),
        )
        val koinEntryProvider = koinEntryProvider<androidx.navigation3.runtime.NavKey>()
        val entryProvider = remember(rootOnlyEntryProvider, koinEntryProvider) {
            { route: androidx.navigation3.runtime.NavKey ->
                if (route == RootTabs) {
                    rootOnlyEntryProvider(route)
                } else {
                    koinEntryProvider(route)
                }
            }
        }

        LaunchedEffect(navigationState) {
            navigator.setNavigationState(navigationState)
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize(),
        ) {
            NavDisplay(
                entries = navigationState.toEntries(entryProvider),
                modifier = Modifier.fillMaxSize(),
                onBack = navigator::goBack,
            )
        }
    }
}
