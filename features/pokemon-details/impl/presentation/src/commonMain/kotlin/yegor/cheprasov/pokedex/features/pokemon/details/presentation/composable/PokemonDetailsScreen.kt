package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsActionUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel

@Composable
internal fun PokemonDetailsScreen(
    state: PokemonDetailsStateUi,
    onAction: (PokemonDetailsActionUi) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
        PokemonDetailsHeader(state, onAction = onAction)
    }
}

@Preview
@Composable
private fun PokemonDetailsScreenPreview() {
    PokedexTheme {
        PokemonDetailsScreen(
            state = PokemonDetailsStateUi(
                pokemonName = "Bulbasaur",
                pokemonType = PokemonTypeUiModel.Grass
            ),
            onAction = {}
        )
    }
}
