package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
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
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.badges.PokedexNumberBadge
import yegor.cheprasov.pokedex.core.design.composable.buttons.BackButton
import yegor.cheprasov.pokedex.core.design.composable.buttons.FavoriteButton
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsIntentUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsLoadStateUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonImage
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonTypeBadge

@Composable
internal fun PokemonDetailsHeader(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    onAction: (PokemonDetailsIntentUi) -> Unit
) {
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }
    Box(modifier = modifier.fillMaxWidth().height(300.dp)) {
        Image(
            painter = painterResource(state.pokemonType.pokemonHeaderBgImage),
            modifier = Modifier.fillMaxWidth().height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
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
                onAction(PokemonDetailsIntentUi.OnBackClick)
            }
            FavoriteButton(isFavorite = state.isFavorite) {
                onAction(PokemonDetailsIntentUi.OnFavoriteClick)
            }
        }
        if (state.detailsState is PokemonDetailsLoadStateUi.Success) {
            val pokemon = state.detailsState.pokemon
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp + 44.dp + statusBarHeight)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PokemonImage(
                    pokemon.imageUrl,
                    modifier = Modifier.size(112.dp)
                        .localSharedElement("pokemon_image_${pokemon.imageUrl}")
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PokedexNumberBadge(
                        number = pokemon.normalizedId,
                        color = PokedexTheme.colors.background,
                        modifier = Modifier.localSharedElement(
                            "pokemon_number_${pokemon.normalizedId}"
                        )
                    )
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        style = PokedexTheme.typography.titleLarge.copy(
                            color = PokedexTheme.colors.textPrimary,
                        ),
                        modifier = Modifier.localSharedElement(
                            "pokemon_name_${pokemon.normalizedId}"
                        )
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        pokemon.pokemonTypes.forEach { type ->
                            PokemonTypeBadge(
                                type = type,
                                backgroundColor = PokedexTheme.colors.background,
                                backgroundAlpha = 1f,
                                modifier = Modifier.localSharedElement(
                                    "pokemon_type_${pokemon.normalizedId}_${type.name}"
                                )
                            )
                        }
                    }
                }
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
