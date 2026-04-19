package yegor.cheprasov.pokedex.core.design.composable.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun ToolbarIndicator(
    color: Color,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        drawCircle(
            color = color,
            radius = this.size.minDimension / 2f
        )
    }
}

@Preview
@Composable
private fun ToolbarIndicatorPreview() {
    ToolbarIndicator(
        color = Color.Blue,
        size = 22.dp
    )
}