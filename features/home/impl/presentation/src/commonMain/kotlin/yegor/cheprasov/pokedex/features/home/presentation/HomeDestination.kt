package yegor.cheprasov.pokedex.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.features.home.presentation.composable.HomeScreen

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    HomeScreen(state, viewModel::onAction)
}
