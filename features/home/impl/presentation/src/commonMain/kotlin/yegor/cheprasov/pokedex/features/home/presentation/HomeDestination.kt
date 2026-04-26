package yegor.cheprasov.pokedex.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.v1rusdev.simplemvi.compose.CollectEffectsUiEvent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.pokemon.list.api.PokemonList
import yegor.cheprasov.pokedex.features.search.api.PokemonSearch
import yegor.cheprasov.pokedex.features.home.presentation.composable.HomeScreen

@Composable
fun HomeDestination(
    navigator: AppNavigator = koinInject(),
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    HomeScreen(state, viewModel::onIntent)

    CollectEffectsUiEvent(viewModel.uiEffects) { event ->
        when (event) {
            HomeEffectUi.OpenSearchScreen -> navigator.navigate(PokemonSearch)
            HomeEffectUi.OpenPokemonListScreen -> navigator.navigate(PokemonList)
        }
    }
}
