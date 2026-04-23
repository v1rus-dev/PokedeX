package yegor.cheprasov.pokedex.core.design.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun Modifier.withAppShadow(
    radius: Dp = 16.dp,
    color: Color = PokedexTheme.colors.surfaceBorder.copy(alpha = 0.05f)
): Modifier = composed {
    dropShadow(
        shape = RoundedCornerShape(radius),
        shadow = Shadow(
            radius = 8.dp,
            spread = 0.dp,
            color = color,
            offset = DpOffset(0.dp, 4.dp),
            alpha = 1f,
            blendMode = BlendMode.SrcOver,
        )
    )
}