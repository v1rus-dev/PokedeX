package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatValueUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatsUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.previewValueRange

@Composable
internal fun PokemonStatsCard(
    stats: List<PokemonStatValueUiModel>,
    modifier: Modifier = Modifier,
    isOpen: Boolean = false,
    onClick: (() -> Unit)? = null
) {
//    Layout(modifier = modifier.fillMaxWidth()) {
//
//    }
}

@Preview
@Composable
private fun PokemonStatsCardPreview() {
    val stats = remember {
        PokemonStatsUiModel.entries.map {
            PokemonStatValueUiModel(
                statsUiModel = it,
                value = it.previewValueRange.random(),
                minValue = it.previewValueRange.first,
                maxValue = it.previewValueRange.last,
            )
        }
    }

    PokedexTheme {
        PokemonStatsCard(stats = stats)
    }
}
