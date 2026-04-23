package yegor.cheprasov.pokedex.core.design.composable.buttons

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.composable.cardSurface
import yegor.cheprasov.pokedex.core.design.composable.icons.BackIcon
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun BackButton(
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
        BackIcon(color = PokedexTheme.colors.textPrimary)
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    PokedexTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackButton() {}
        }
    }
}