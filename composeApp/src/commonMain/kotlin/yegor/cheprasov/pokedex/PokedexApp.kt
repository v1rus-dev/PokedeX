package yegor.cheprasov.pokedex

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.koinInject
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalSharedTransitionScope
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.navigationConfiguration
import yegor.cheprasov.pokedex.core.design.navigation.rememberAppNavigationState
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.favorites.presentation.favoritesTopLevelDestination
import yegor.cheprasov.pokedex.features.home.presentation.homeTopLevelDestination
import yegor.cheprasov.pokedex.features.pokemon.details.api.pokemonDetailsSerializersModule
import yegor.cheprasov.pokedex.features.root.presentation.RootTabs
import yegor.cheprasov.pokedex.features.root.presentation.rootEntryProvider
import yegor.cheprasov.pokedex.features.root.presentation.rootTabsNavigationSerializersModule
import yegor.cheprasov.pokedex.features.search.api.pokemonSearchNavigationSerializersModule
import yegor.cheprasov.pokedex.features.settings.presentation.settingsTopLevelNavigation

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun PokedexApp() {
    val mainViewModel: MainViewModel = koinViewModel()
    val state by mainViewModel.uiState.collectAsState()

    PokedexTheme(themeMode = state.themeMode) {
        val topLevelDestinations = remember {
            listOf(
                homeTopLevelDestination,
                favoritesTopLevelDestination,
                settingsTopLevelNavigation
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
                    pokemonSearchNavigationSerializersModule,
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

        SharedTransitionLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            ProvideLocalSharedTransitionScope(sharedTransitionScope = this) {
                NavDisplay(
                    entries = navigationState.toEntries(entryProvider),
                    sharedTransitionScope = this,
                    onBack = navigator::popBackStack,
                )
            }
        }
    }
}
