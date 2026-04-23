package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Stable

@Stable
data class PokemonUiModel(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val pokemonTypes: List<PokemonTypeUiModel>
) {
    companion object {
        val PREVIEW = PokemonUiModel(
            name = "Charmander",
            id = 3,
            imageUrl = "",
            pokemonTypes = listOf(PokemonTypeUiModel.Fire)
        )
    }

    val mainType: PokemonTypeUiModel = pokemonTypes.firstOrNull() ?: PokemonTypeUiModel.Unknown
}
