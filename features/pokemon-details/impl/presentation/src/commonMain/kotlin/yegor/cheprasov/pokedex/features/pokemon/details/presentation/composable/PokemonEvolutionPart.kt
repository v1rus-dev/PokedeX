package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsIntentUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi

@Composable
internal fun PokemonEvolutionPart(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    onIntent: (PokemonDetailsIntentUi) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {

    }
}

@Composable
private fun EvolutionPokemonCard()

@Preview
@Composable
private fun PokemonEvolutionPartPreview() {
    PokedexTheme {
        PokemonEvolutionPart(state = PokemonDetailsStateUi.PREVIEW, onIntent = {})
    }
}