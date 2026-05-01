package yegor.cheprasov.pokedex.features.pokemon.details.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.presentation.PokemonDetailsStateUi
import yegor.cheprasov.pokedex.features.pokemon.ui.composable.PokemonTypeBadge

@Composable
internal fun PokemonBaseInfoPart(
    state: PokemonDetailsStateUi,
    modifier: Modifier = Modifier
) {
    val pokemonName = state.pokemon.name.replaceFirstChar { it.uppercase() }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = pokemonName,
            style = PokedexTheme.typography.titleLarge.copy(
                color = PokedexTheme.colors.textPrimary,
            ),
            modifier = Modifier.localSharedElement(
                "pokemon_name_${state.pokemon.normalizedId}"
            )
        )
        Text(
            text = "#${state.pokemon.normalizedId}",
            style = PokedexTheme.typography.labelSmall.copy(PokedexTheme.colors.textSecondary),
            modifier = Modifier.localSharedElement(
                "pokemon_number_${state.pokemon.normalizedId}"
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (state.pokemon.pokemonTypes.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                state.pokemon.pokemonTypes.forEach {
                    PokemonTypeBadge(it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonBaseInfoPartPreview() {
    PokedexTheme {
        PokemonBaseInfoPart(state = PokemonDetailsStateUi.PREVIEW)
    }
}