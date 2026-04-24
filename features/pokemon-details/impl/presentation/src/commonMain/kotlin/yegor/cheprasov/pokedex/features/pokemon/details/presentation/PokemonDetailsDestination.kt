package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import yegor.cheprasov.pokedex.core.design.composable.effects.CollectEventsUiEffect
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable.PokemonDetailsScreen
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

@Composable
internal fun PokemonDetailsDestination(
    pokemonName: String,
    pokemonType: PokemonType,
    viewModel: PokemonDetailsViewModel = koinViewModel(
        parameters = { parametersOf(pokemonName, pokemonType) }
    ),
    appNavigator: AppNavigator = koinInject()
) {
    val state by viewModel.uiState.collectAsState()

    PokemonDetailsScreen(state, viewModel::onAction)

    CollectEventsUiEffect(eventsFlow = viewModel.uiEvents) { event ->
        when(event) {
            PokemonDetailsEventUi.CloseScreen -> appNavigator.popBackStack()
        }
    }
}
