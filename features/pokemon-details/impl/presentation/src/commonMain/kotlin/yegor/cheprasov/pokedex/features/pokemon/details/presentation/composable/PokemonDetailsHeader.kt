package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import yegor.cheprasov.pokedex.core.design.composable.buttons.BackButton
import yegor.cheprasov.pokedex.core.design.composable.buttons.FavoriteButton
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsActionUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi

@Composable
internal fun PokemonDetailsHeader(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    onAction: (PokemonDetailsActionUi) -> Unit
) {
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(state.pokemonType.pokemonHeaderBgImage),
            modifier = Modifier.fillMaxWidth().height(300.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp + statusBarHeight),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton {
                onAction(PokemonDetailsActionUi.OnBackClick)
            }
            FavoriteButton(isFavorite = state.isFavorite) {
                onAction(PokemonDetailsActionUi.OnFavoriteClick)
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailsHeaderPreview() {
    PokedexTheme {
        PokemonDetailsHeader(state = PokemonDetailsStateUi.PREVIEW) {}
    }
}