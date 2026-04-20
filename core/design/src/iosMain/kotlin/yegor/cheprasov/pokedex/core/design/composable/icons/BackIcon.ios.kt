package yegor.cheprasov.pokedex.core.design.composable.icons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_chevron_left

@Composable
actual fun BackIcon(modifier: Modifier, color: Color) {
    Icon(painter = painterResource(Res.drawable.ic_chevron_left), modifier = modifier, contentDescription = null)
}