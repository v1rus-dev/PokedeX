package yegor.cheprasov.pokedex.core.design.composable.toolbars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yegor.cheprasov.pokedex.core.design.composable.utils.ToolbarIndicator
import yegor.cheprasov.pokedex.core.design.ext.roundedCornerTo

private val BackgroundColor = Color(0xFFDC0A2D)

private val indicators = listOf(
    Color(0xFF38A8DB),
    Color(0xFFE5BB04),
    Color(0xFF28C650)
)

@Composable
fun RootPokedexTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    val largeHeight = 64.dp
    val smallHeight = 36.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(largeHeight)
            .background(Color.Transparent)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val largeHeightPx = largeHeight.toPx()
            val smallHeightPx = smallHeight.toPx()

            val leftBottom = Offset(0f, largeHeightPx)
            val firstCorner = Offset(size.width / 2f + 35.dp.toPx(), largeHeightPx)
            val secondCorner = Offset(size.width / 2f + 80.dp.toPx(), smallHeightPx)
            val rightBottom = Offset(size.width, smallHeightPx)
            val cornerRadius = 12.dp.toPx()

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, largeHeightPx)

                roundedCornerTo(
                    previous = leftBottom,
                    corner = firstCorner,
                    next = secondCorner,
                    radius = cornerRadius
                )

                roundedCornerTo(
                    previous = firstCorner,
                    corner = secondCorner,
                    next = rightBottom,
                    radius = cornerRadius
                )

                lineTo(rightBottom.x, rightBottom.y)
                lineTo(size.width, 0f)
                close()
            }

            drawPath(path = path, color = BackgroundColor)
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )

        Row(
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 12.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            indicators.forEach { color ->
                ToolbarIndicator(color, size = 12.dp)
            }
        }
    }
}

@Preview
@Composable
private fun RootPokedexTopAppBarPreview() {
    Scaffold(
        topBar = { RootPokedexTopAppBar(title = "Pokedex") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(30) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "Item $index", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
