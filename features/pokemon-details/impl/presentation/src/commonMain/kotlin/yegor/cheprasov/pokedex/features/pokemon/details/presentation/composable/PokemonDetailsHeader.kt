package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import yegor.cheprasov.pokedex.core.design.composable.buttons.BackButton
import yegor.cheprasov.pokedex.core.design.composable.buttons.FavoriteButton
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsActionUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsLoadStateUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonImage

@Composable
internal fun PokemonDetailsHeader(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    onAction: (PokemonDetailsActionUi) -> Unit
) {
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }
    Box(modifier = modifier.fillMaxWidth().height(300.dp)) {
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
        if (state.detailsState is PokemonDetailsLoadStateUi.Success) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp + 44.dp + statusBarHeight)
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.width(50.dp)) {
                    NumberChip(state.detailsState.pokemon.normalizedId)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = PokedexTheme.colors.background),
                    shape = CircleShape
                ) {
                    PokemonImage(
                        state.detailsState.pokemon.imageUrl,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberChip(number: String, modifier: Modifier = Modifier) {
    Text(
        text = "#$number",
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PokedexTheme.colors.background)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = PokedexTheme.typography.bodySmall.copy(
            color = PokedexTheme.colors.textSecondary
        ),
    )
}

@Preview
@Composable
private fun PokemonDetailsHeaderPreview() {
    PokedexTheme {
        PokemonDetailsHeader(state = PokemonDetailsStateUi.PREVIEW) {}
    }
}