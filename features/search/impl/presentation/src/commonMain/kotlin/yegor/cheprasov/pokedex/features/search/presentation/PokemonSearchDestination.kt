package yegor.cheprasov.pokedex.features.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.v1rusdev.simplemvi.compose.CollectEffectsUiEvent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
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
        onIntent = viewModel::onIntent,
    )

    CollectEffectsUiEvent(viewModel.uiEffects) { effect ->
        when(effect) {
            PokemonSearchEffectUi.CloseScreen -> appNavigator.popBackStack()
        }
    }
}
