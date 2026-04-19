package yegor.cheprasov.pokedex.features.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.features.settings.presentation.composable.SettingsScreen

@Composable
fun SettingsDestination(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}
