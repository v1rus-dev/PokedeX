package yegor.cheprasov.pokedex.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
internal data class PokedexSystemBarsStyle(
    val statusBarColor: Color,
    val navigationBarColor: Color,
    val preferLightStatusBarIcons: Boolean,
    val preferLightNavigationBarIcons: Boolean,
)

@Composable
internal expect fun PokedexSystemBarsEffect(style: PokedexSystemBarsStyle)
