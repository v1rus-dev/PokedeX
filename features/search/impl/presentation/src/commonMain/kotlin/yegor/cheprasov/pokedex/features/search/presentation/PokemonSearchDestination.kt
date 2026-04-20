package yegor.cheprasov.pokedex.features.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.core.design.composable.effects.CollectEventsUiEffect
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.search.presentation.composable.PokemonSearchScreen

@Composable
fun PokemonSearchDestination(
    appNavigator: AppNavigator = koinInject(),
    viewModel: PokemonSearchViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    PokemonSearchScreen(
        state = state,
        onAction = viewModel::onAction,
    )

    CollectEventsUiEffect(viewModel.uiEvents) { event ->
        when(event) {
            PokemonSearchEventUi.CloseScreen -> appNavigator.popBackStack()
        }
    }
}
