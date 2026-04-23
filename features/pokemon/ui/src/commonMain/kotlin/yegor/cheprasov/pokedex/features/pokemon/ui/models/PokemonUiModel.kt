package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Stable

@Stable
data class PokemonUiModel(
    val name: String,
    val pokemonTypes: List<PokemonTypeUiModel>
) {
    companion object {
        val PREVIEW = PokemonUiModel(
            name = "Charmander",
            pokemonTypes = listOf(PokemonTypeUiModel.Fire)
        )
    }
}
