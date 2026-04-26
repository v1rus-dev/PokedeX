package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class PokemonStatValueUiModel(
    val statsUiModel: PokemonStatsUiModel,
    val value: Int,
    val minValue: Int,
    val maxValue: Int,
) {
    val percentOfMax: Float = calculatePercentOfMax(
        value = value,
        maxValue = maxValue,
    )

    val normalizedPercent: Float = calculateNormalizedPercent(
        value = value,
        minValue = minValue,
        maxValue = maxValue,
    )
}

private fun calculatePercentOfMax(value: Int, maxValue: Int): Float {
    if (maxValue <= 0) return 0f

    return (value.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f)
}

private fun calculateNormalizedPercent(value: Int, minValue: Int, maxValue: Int): Float {
    if (maxValue <= 0) return 0f

    val range = maxValue - minValue
    if (range <= 0) {
        return if (value > 0) 1f else 0f
    }

    return ((value - minValue).toFloat() / range.toFloat()).coerceIn(0f, 1f)
}
