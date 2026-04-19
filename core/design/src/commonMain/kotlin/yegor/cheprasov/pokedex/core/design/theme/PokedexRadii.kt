package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class PokedexRadii(
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val full: Dp = 999.dp,
)

internal val LocalPokedexRadii = staticCompositionLocalOf { PokedexRadii() }
