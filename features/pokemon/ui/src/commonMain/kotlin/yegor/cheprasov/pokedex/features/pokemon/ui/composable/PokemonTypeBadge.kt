package yegor.cheprasov.pokedex.features.pokemon.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel

@Composable
fun PokemonTypeBadge(
    type: PokemonTypeUiModel,
    modifier: Modifier = Modifier,
    backgroundAlpha: Float = 0.15f,
    backgroundColor: Color = type.colors.primary,
    textColor: Color = type.colors.primary,
    onClick: (() -> Unit)? = null
) {
    Text(
        text = type.name,
        style = PokedexTheme.typography.labelSmall.copy(
            color = textColor,
        ),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor.copy(alpha = backgroundAlpha))
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}

@Preview
@Composable
private fun PokemonTypeBadgePreview() {
    PokedexTheme {
        Column {
            PokemonTypeBadge(type = PokemonTypeUiModel.Grass)
        }
    }
}
