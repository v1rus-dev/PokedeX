package yegor.cheprasov.pokedex.features.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.features.search.presentation.composable.PokemonSearchScreen

@Composable
fun PokemonSearchDestination(
    onBack: () -> Unit,
    viewModel: PokemonSearchViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    PokemonSearchScreen(
        state = state,
        onBack = onBack,
        onAction = viewModel::onAction,
    )
}
