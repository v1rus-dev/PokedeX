package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Stable

@Stable
data class PokemonEvolutionChainUiModel(
    val pokemons: List<PokemonUiModel>,
)
