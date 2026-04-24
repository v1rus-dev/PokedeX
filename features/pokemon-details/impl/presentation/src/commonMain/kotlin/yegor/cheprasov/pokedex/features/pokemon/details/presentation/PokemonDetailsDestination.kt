package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import yegor.cheprasov.pokedex.core.design.composable.effects.CollectEventsUiEffect
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable.PokemonDetailsScreen

@Composable
internal fun PokemonDetailsDestination(
    pokemonName: String,
    viewModel: PokemonDetailsViewModel = koinViewModel(
        parameters = { parametersOf(pokemonName) }
    )
) {
    val state by viewModel.uiState.collectAsState()

    PokemonDetailsScreen(state, viewModel::onAction)

    CollectEventsUiEffect(eventsFlow = viewModel.uiEvents) { event ->
        when(event) {
            else -> Unit
        }
    }
}
