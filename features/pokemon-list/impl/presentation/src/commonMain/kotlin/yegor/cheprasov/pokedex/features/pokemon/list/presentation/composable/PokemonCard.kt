package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
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
            // Номер
            Text(
                text = "#${pokemon.normalizedId}",
                style = PokedexTheme.typography.labelSmall.copy(
                    color = PokedexTheme.colors.textSecondary
                ),
                modifier = Modifier.width(50.dp)
            )

            if (pokemon.imageUrl.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(pokemon.mainType.colors.gradientStart.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            // Имя + бейджи
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = PokedexTheme.typography.titleMedium
                )
                if (pokemon.pokemonTypes.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        pokemon.pokemonTypes.forEach { type ->
                            Text(
                                text = type.name,
                                style = PokedexTheme.typography.labelSmall.copy(
                                    color = type.colors.primary,
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(type.colors.primary.copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
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
        Column {
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