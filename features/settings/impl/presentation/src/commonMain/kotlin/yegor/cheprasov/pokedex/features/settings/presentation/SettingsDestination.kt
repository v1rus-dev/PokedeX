package yegor.cheprasov.pokedex.features.settings.presentation

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.features.settings.presentation.composable.SettingsScreen

@Composable
fun SettingsDestination(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    SettingsScreen()
}
