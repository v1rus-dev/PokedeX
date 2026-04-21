package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListActionUi
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

@Composable
internal fun PokemonItem(
    pokemon: PokemonUiModel,
    modifier: Modifier = Modifier,
    onAction: (PokemonListActionUi) -> Unit
) {
    val spacing = PokedexTheme.spacing
    val radii = PokedexTheme.radii
    val colors = PokedexTheme.colors

    Surface(
        shape = RoundedCornerShape(radii.large),
        color = colors.listSurface,
        contentColor = colors.textPrimary,
        tonalElevation = spacing.xSmall,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colors.surfaceBorder,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.large, vertical = spacing.small)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
                onClick = {
                    onAction(PokemonListActionUi.ClickPokemon(pokemon.name))
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.large, vertical = spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(colors.listAccent),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = pokemon.url,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.size(spacing.large))
            Column {
                Text(
                    text = pokemon.name.replaceFirstChar { char -> char.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    modifier = Modifier.padding(top = spacing.xSmall),
                    text = "Tap to open details",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PokemonItemPreview() {
    PokemonItem(pokemon = PokemonUiModel.PREVIEW, onAction = {})
}
