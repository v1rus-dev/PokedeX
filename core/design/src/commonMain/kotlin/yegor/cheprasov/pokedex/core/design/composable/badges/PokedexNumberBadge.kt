package yegor.cheprasov.pokedex.core.design.composable.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun PokedexNumberBadge(
    number: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = "#$number",
        style = PokedexTheme.typography.labelSmall.copy(
            color = PokedexTheme.colors.textSecondary,
        ),
        maxLines = 1,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    )
}

@Preview
@Composable
private fun PokedexNumberBadgePreview() {
    PokedexTheme {
        Column {
            PokedexNumberBadge(
                number = "0001",
                color = PokedexTheme.colors.primary.copy(alpha = 0.12f)
            )
        }
    }
}
