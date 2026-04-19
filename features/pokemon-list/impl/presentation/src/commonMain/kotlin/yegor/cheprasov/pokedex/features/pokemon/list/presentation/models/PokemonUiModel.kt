package yegor.cheprasov.pokedex.features.pokemon.list.presentation.models

import androidx.compose.runtime.Stable

@Stable
data class PokemonUiModel(
    val name: String,
    val url: String?,
) {
    companion object {
        val PREVIEW = PokemonUiModel(
            name = "Charmander",
            url = ""
        )
    }
}
