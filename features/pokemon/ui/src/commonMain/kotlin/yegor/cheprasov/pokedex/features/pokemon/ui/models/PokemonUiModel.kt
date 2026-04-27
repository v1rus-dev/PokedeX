package yegor.cheprasov.pokedex.features.pokemon.ui.models

import androidx.compose.runtime.Stable

@Stable
data class PokemonUiModel(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val pokemonTypes: List<PokemonTypeUiModel>,
    val stats: List<PokemonStatValueUiModel>
) {
    companion object {
        val PREVIEW = PokemonUiModel(
            name = "Charmander",
            id = 3,
            imageUrl = "",
            pokemonTypes = listOf(PokemonTypeUiModel.Fire),
            stats = PokemonStatsUiModel.entries.map {
                PokemonStatValueUiModel(
                    statsUiModel = it,
                    value = 5,
                    minValue = it.previewValueRange.first,
                    maxValue = it.previewValueRange.last,
                )
            }
        )

        fun fromNavArgs(name: String, typeUiModel: PokemonTypeUiModel) = PokemonUiModel(
            name = name,
            id = 0,
            imageUrl = "",
            pokemonTypes = listOf(typeUiModel),
            stats = emptyList()
        )
    }

    val mainType: PokemonTypeUiModel = pokemonTypes.firstOrNull() ?: PokemonTypeUiModel.Unknown

    val normalizedId: String = id.toString().padStart(3, '0')
}
