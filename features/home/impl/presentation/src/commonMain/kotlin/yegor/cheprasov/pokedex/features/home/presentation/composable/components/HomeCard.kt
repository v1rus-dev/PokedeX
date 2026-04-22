package yegor.cheprasov.pokedex.features.home.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_pokeball_bg_icon
import pokedex.core.resources.generated.resources.pokemons
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardModelUi
import yegor.cheprasov.pokedex.features.home.presentation.models.HomeMainCardTypeUi

@Composable
internal fun HomeCard(
    model: HomeMainCardModelUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = PokedexTheme.colors
    val radii = PokedexTheme.radii
    val typography = PokedexTheme.typography
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(radii.medium)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape)
            .background(model.backgroundColor)
            .drawWithCache {
                val primaryCircleColor = colors.background.copy(alpha = 0.18f)

                val topLeftRadius = size.minDimension * 0.6f

                onDrawWithContent {
                    drawCircle(
                        color = primaryCircleColor,
                        radius = topLeftRadius,
                        center = Offset(
                            x = -topLeftRadius * 0.2f,
                            y = -topLeftRadius * 0.15f,
                        ),
                    )
                    drawContent()
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick,
            ),
    ) {
        Icon(
            painter = painterResource(model.icon),
            contentDescription = null,
            tint = colors.background.copy(alpha = 0.18f),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 26.dp, y = (-2).dp)
                .size(92.dp)
                .rotate(model.iconRotation)
        )

        Text(
            text = stringResource(model.label),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = typography.titleLarge.copy(color = colors.onPrimary),
        )
    }
}

@Preview
@Composable
private fun HomeCardPreview() {
    PokedexTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            HomeCard(
                model = HomeMainCardModelUi(
                    type = HomeMainCardTypeUi.POKEMONS,
                    label = Res.string.pokemons,
                    icon = Res.drawable.ic_pokeball_bg_icon,
                    backgroundColor = PokedexTheme.colors.primary,
                ),
                modifier = Modifier.padding(16.dp),
                onClick = {},
            )
        }
    }
}
