package yegor.cheprasov.pokedex.core.design.composable.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_heart_empty
import pokedex.core.resources.generated.resources.ic_heart_full
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = ripple(),
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .cardSurface(shape = RoundedCornerShape(PokedexTheme.radii.small))
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(isFavorite) {
            if (isFavorite) {
                Icon(painter = painterResource(Res.drawable.ic_heart_full), contentDescription = null)
            } else {
                Icon(painter = painterResource(Res.drawable.ic_heart_empty), contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
private fun FavoriteButtonPreview() {
    var isFavorite by remember { mutableStateOf(false) }
    PokedexTheme {
        Column {
            FavoriteButton(isFavorite) {
                isFavorite = !isFavorite
            }
        }
    }
}