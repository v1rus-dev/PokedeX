package yegor.cheprasov.pokedex.features.favorites.presentation

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import yegor.cheprasov.pokedex.features.favorites.presentation.composable.PokemonFavoritesScreen

@Composable
fun PokemonFavoritesDestination(
    viewModel: PokemonFavoritesViewModel = koinViewModel(),
) {
    PokemonFavoritesScreen()
}

