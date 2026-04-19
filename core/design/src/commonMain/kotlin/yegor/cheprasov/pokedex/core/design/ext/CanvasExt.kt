package yegor.cheprasov.pokedex.core.design.ext

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.min
import kotlin.math.sqrt

internal fun Path.roundedCornerTo(
    previous: Offset,
    corner: Offset,
    next: Offset,
    radius: Float
) {
    val toPrevious = (previous - corner).normalized()
    val toNext = (next - corner).normalized()

    val maxRadius = min(
        (previous - corner).length() / 2f,
        (next - corner).length() / 2f
    )
    val clampedRadius = min(radius, maxRadius)

    val start = corner + toPrevious * clampedRadius
    val end = corner + toNext * clampedRadius

    lineTo(start.x, start.y)
    quadraticBezierTo(corner.x, corner.y, end.x, end.y)
}

internal fun Offset.length(): Float = sqrt(x * x + y * y)

internal fun Offset.normalized(): Offset {
    val length = length()
    return if (length == 0f) Offset.Zero else Offset(x / length, y / length)
}

internal operator fun Offset.plus(other: Offset): Offset = Offset(x + other.x, y + other.y)

internal operator fun Offset.minus(other: Offset): Offset = Offset(x - other.x, y - other.y)

internal operator fun Offset.times(value: Float): Offset = Offset(x * value, y * value)