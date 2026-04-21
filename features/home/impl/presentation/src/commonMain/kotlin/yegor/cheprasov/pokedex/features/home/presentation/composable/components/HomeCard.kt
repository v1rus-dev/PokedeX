package yegor.cheprasov.pokedex.features.home.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
internal fun HomeCard(
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = PokedexTheme.colors
    val radii = PokedexTheme.radii
    val typography = PokedexTheme.typography
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(radii.large)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(104.dp)
            .clip(shape)
            .background(backgroundColor)
            .drawWithCache {
                val primaryCircleColor = colors.background.copy(alpha = 0.18f)
                val secondaryCircleColor = colors.background.copy(alpha = 0.11f)
                val largeRadius = size.minDimension * 0.52f
                val topLeftRadius = size.minDimension * 0.6f
                val mediumRadius = size.minDimension * 0.3f
                val smallRadius = size.minDimension * 0.18f

                onDrawWithContent {
                    drawCircle(
                        color = primaryCircleColor,
                        radius = topLeftRadius,
                        center = Offset(
                            x = -topLeftRadius * 0.2f,
                            y = -topLeftRadius * 0.15f,
                        ),
                    )
                    drawCircle(
                        color = primaryCircleColor,
                        radius = largeRadius,
                        center = Offset(
                            x = size.width * 0.88f,
                            y = size.height * 0.18f,
                        ),
                    )
                    drawCircle(
                        color = secondaryCircleColor,
                        radius = mediumRadius,
                        center = Offset(
                            x = size.width * 0.72f,
                            y = size.height * 0.78f,
                        ),
                    )
                    drawCircle(
                        color = secondaryCircleColor,
                        radius = smallRadius,
                        center = Offset(
                            x = size.width * 0.93f,
                            y = size.height * 0.64f,
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
        contentAlignment = Alignment.BottomStart,
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
                title = "Pokemons",
                backgroundColor = PokedexTheme.colors.primary,
                modifier = Modifier.padding(16.dp),
                onClick = {},
            )
        }
    }
}
