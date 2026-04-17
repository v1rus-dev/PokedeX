package yegor.cheprasov.pokedex.features.pokemon.list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.core.design.composable.effects.CollectEventsUiEffect
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable.PokemonListScreen

@Composable
fun PokemonListDestination(
    viewModel: PokemonListViewModel = koinViewModel(),
    onOpenPokemon: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    PokemonListScreen(
        state = uiState,
        onAction = viewModel::onAction,
    )

    CollectEventsUiEffect(viewModel.uiEvents) { event ->
        when(event) {
            is PokemonListEventUi.NavigateToPokemonDetail -> onOpenPokemon(event.name)
        }
    }
}
