package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.badges.PokedexNumberBadge
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonImage
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonTypeBadge
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

@Composable
internal fun PokemonCard(
    pokemon: PokemonUiModel,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .background(PokedexTheme.colors.surface)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .cardSurface(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            pokemon.mainType.colors.gradientStart,
                            pokemon.mainType.colors.gradientEnd,
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (pokemon.imageUrl.isNotEmpty() || LocalInspectionMode.current) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(pokemon.mainType.colors.gradientStart.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    PokemonImage(
                        pokemon.imageUrl,
                        modifier = Modifier.size(60.dp)
                            .localSharedElement("pokemon_image_${pokemon.imageUrl}")
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        style = PokedexTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(1f)
                            .localSharedElement("pokemon_name_${pokemon.normalizedId}")
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    PokedexNumberBadge(
                        number = pokemon.normalizedId,
                        color = PokedexTheme.colors.primary.copy(alpha = 0.12f),
                        modifier = Modifier.localSharedElement(
                            "pokemon_number_${pokemon.normalizedId}"
                        )
                    )
                }
                if (pokemon.pokemonTypes.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        pokemon.pokemonTypes.forEach { type ->
                            PokemonTypeBadge(
                                type = type,
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
private fun PokemonCardPreview() {
    PokedexTheme {
        Column(modifier = Modifier.fillMaxSize().background(PokedexTheme.colors.background)) {
            PokemonCard(
                pokemon = PokemonUiModel(
                    name = "Bulbasaur", 1, imageUrl = "", pokemonTypes = listOf(
                        PokemonTypeUiModel.Grass,
                        PokemonTypeUiModel.Ground
                    )
                )
            ) {}
        }
    }
}
