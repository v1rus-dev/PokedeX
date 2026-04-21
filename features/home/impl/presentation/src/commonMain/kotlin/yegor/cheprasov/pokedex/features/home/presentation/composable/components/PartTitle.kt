package yegor.cheprasov.pokedex.features.home.presentation.composable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_arrow_right
import pokedex.core.resources.generated.resources.ic_chevron_right
import pokedex.core.resources.generated.resources.see_more
import yegor.cheprasov.pokedex.core.common.platform.currentPlatform
import yegor.cheprasov.pokedex.core.common.platform.isAndroid
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
internal fun PartTitle(
    title: String,
    modifier: Modifier = Modifier,
    seeMore: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = PokedexTheme.typography.titleLarge)

        Spacer(modifier = Modifier.width(16.dp))
        seeMore?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = it
                )
            ) {
                Text(
                    text = stringResource(Res.string.see_more),
                    style = PokedexTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(
                        if (currentPlatform.isAndroid) {
                            Res.drawable.ic_arrow_right
                        } else {
                            Res.drawable.ic_chevron_right
                        }
                    ),
                    contentDescription = null,
                    tint = PokedexTheme.colors.textPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PartTitlePreview() {
    PokedexTheme {
        PartTitle(title = "Pokemons") {}
    }
}