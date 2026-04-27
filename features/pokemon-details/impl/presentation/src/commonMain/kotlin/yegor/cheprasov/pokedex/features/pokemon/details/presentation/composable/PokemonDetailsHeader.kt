package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.badges.PokedexNumberBadge
import yegor.cheprasov.pokedex.core.design.composable.buttons.BackButton
import yegor.cheprasov.pokedex.core.design.composable.buttons.FavoriteButton
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsIntentUi
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonImage
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonTypeBadge

@Composable
internal fun PokemonDetailsHeader(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier,
    overlapFraction: Float = 0f,
    onAction: (PokemonDetailsIntentUi) -> Unit
) {
    val pokemonName = state.pokemon.name.replaceFirstChar { it.uppercase() }
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }

    val internalOverlapFraction = overlapFraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val topHeaderElevation = if (internalOverlapFraction >= 1f) 5.dp else 0.dp

    Box(modifier = modifier.fillMaxWidth().height(HeaderHeight)) {
        Image(
            painter = painterResource(state.pokemon.mainType.pokemonHeaderBgImage),
            modifier = Modifier.fillMaxWidth().height(HeaderHeight),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HeaderBottomFadeHeight)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            PokedexTheme.colors.background.copy(alpha = 0f),
                            PokedexTheme.colors.background,
                        )
                    )
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = topHeaderElevation)
                .background(PokedexTheme.colors.background.copy(alpha = internalOverlapFraction))
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp + statusBarHeight, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton {
                onAction(PokemonDetailsIntentUi.OnBackClick)
            }
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedVisibility(internalOverlapFraction == 1f, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    pokemonName,
                    style = PokedexTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            FavoriteButton(isFavorite = state.isFavorite) {
                onAction(PokemonDetailsIntentUi.OnFavoriteClick)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp + 44.dp + statusBarHeight)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonImage(
                state.pokemon.imageUrl,
                modifier = Modifier.size(112.dp)
                    .localSharedElement("pokemon_image_${state.pokemon.imageUrl}")
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PokedexNumberBadge(
                    number = state.pokemon.normalizedId,
                    color = PokedexTheme.colors.background,
                    modifier = Modifier.localSharedElement(
                        "pokemon_number_${state.pokemon.normalizedId}"
                    )
                )
                Text(
                    text = pokemonName,
                    style = PokedexTheme.typography.titleLarge.copy(
                        color = PokedexTheme.colors.textPrimary,
                    ),
                    modifier = Modifier.localSharedElement(
                        "pokemon_name_${state.pokemon.normalizedId}"
                    )
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    state.pokemon.pokemonTypes.forEach { type ->
                        PokemonTypeBadge(
                            type = type,
                            backgroundColor = PokedexTheme.colors.background,
                            backgroundAlpha = 1f,
                            modifier = Modifier.localSharedElement(
                                "pokemon_type_${state.pokemon.normalizedId}_${type.name}"
                            )
                        )
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

val HeaderHeight = 260.dp
private val HeaderBottomFadeHeight = 96.dp
