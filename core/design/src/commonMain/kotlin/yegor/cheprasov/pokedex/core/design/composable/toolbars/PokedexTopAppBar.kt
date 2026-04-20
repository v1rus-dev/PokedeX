package yegor.cheprasov.pokedex.core.design.composable.toolbars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.ui.theme.LocalPokedexColors
import yegor.cheprasov.pokedex.core.design.composable.icons.BackIcon
import yegor.cheprasov.pokedex.core.design.composable.utils.ToolbarIndicator
import yegor.cheprasov.pokedex.core.design.ext.roundedCornerTo
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme

@Composable
fun PokedexTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val spacing = PokedexTheme.spacing
    val colors = PokedexTheme.colors
    val typography = PokedexTheme.typography

    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }

    val largeHeight = statusBarHeight + 64.dp
    val smallHeight = statusBarHeight + 36.dp

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

            drawPath(path = path, color = colors.topAppBar)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = if (onBack == null)
                    spacing.large else spacing.small,
                top = (if (onBack == null) spacing.large else spacing.medium) + statusBarHeight
            )
        ) {
            onBack?.let {
                IconButton(onClick = it) {
                    BackIcon(color = LocalPokedexColors.current.topAppBarIcon)
                }

                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = title,
                color = colors.onTopAppBar,
                style = typography.headlineMedium,
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = spacing.medium + statusBarHeight,
                    end = spacing.large,
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                colors.toolbarIndicatorBlue,
                colors.toolbarIndicatorYellow,
                colors.toolbarIndicatorGreen,
            ).forEach { color ->
                ToolbarIndicator(color, size = 12.dp)
            }
        }
    }
}

@Preview
@Composable
private fun PokedexTopAppBarPreview() {
    PokedexTheme {
        Scaffold(
            topBar = { PokedexTopAppBar(title = "Pokedex") }
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
                            Text(
                                text = "Item $index",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokedexTopAppBarWithOnBackPreview() {
    PokedexTheme {
        Scaffold(
            topBar = { PokedexTopAppBar(title = "Pokedex", onBack = {}) }
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
                            Text(
                                text = "Item $index",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
