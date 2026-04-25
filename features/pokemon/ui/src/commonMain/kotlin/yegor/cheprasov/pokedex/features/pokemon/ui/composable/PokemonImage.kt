package yegor.cheprasov.pokedex.features.pokemon.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.bulbasaur_preview
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun PokemonImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1f
) {
    val isPreview = LocalInspectionMode.current
    if (isPreview) {
        Image(
            painter = painterResource(Res.drawable.bulbasaur_preview),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            alpha = alpha
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            alpha = alpha
        )
    }
}

@Preview
@Composable
private fun PokemonImagePreview() {
    PokedexTheme {
        PokemonImage(imageUrl = "")
    }
}