package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListActionUi
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.models.PokemonUiModel

@Composable
internal fun PokemonItem(
    pokemon: PokemonUiModel,
    modifier: Modifier = Modifier,
    onAction: (PokemonListActionUi) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {
                    onAction(PokemonListActionUi.ClickPokemon(pokemon.name))
                })
    ) {
        AsyncImage(
            model = pokemon.url,
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Text(text = pokemon.name)
    }
}

@Preview
@Composable
private fun PokemonItemPreview() {
    PokemonItem(pokemon = PokemonUiModel.PREVIEW, onAction = {})
}