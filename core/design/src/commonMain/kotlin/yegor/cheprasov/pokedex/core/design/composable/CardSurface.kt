package yegor.cheprasov.pokedex.core.design.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun Modifier.cardSurface(
    shape: Shape = RoundedCornerShape(16.dp),
    shadowColor: Color = PokedexTheme.colors.shadowColor,
    color: Color = PokedexTheme.colors.surface,
    radius: Dp = 4.dp
): Modifier = this
    .dropShadow(shape, Shadow(
        radius = radius,
        offset = DpOffset(x = 0.dp, y = 4.dp),
        color = shadowColor,
        spread = 0.dp
    )
    )
    .border(0.5.dp, Color.Black.copy(alpha = 0.06f), shape)
    .clip(shape)
    .background(color, shape)