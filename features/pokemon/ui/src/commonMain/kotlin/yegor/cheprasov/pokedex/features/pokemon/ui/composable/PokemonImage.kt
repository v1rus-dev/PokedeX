package yegor.cheprasov.pokedex.features.pokemon.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun PokemonImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val isPreview = LocalInspectionMode.current
    AsyncImage(
        model = imageUrl,
        modifier = modifier,
        contentDescription = null
    )
}

@Preview
@Composable
private fun PokemonImagePreview() {
    PokedexTheme {
        PokemonImage(imageUrl = "")
    }
}