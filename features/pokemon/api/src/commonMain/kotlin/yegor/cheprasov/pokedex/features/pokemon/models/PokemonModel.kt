package yegor.cheprasov.pokedex.features.pokemon.models

data class PokemonModel(
    val id: Int,
    val name: String,
    val isFavorite: Boolean,
    val height: Int,
    val weight: Int,
    val baseExperience: Int?,
    val types: List<PokemonType>,
    val sprites: PokemonSprites,
) {
    val imageUrl: String? = sprites.frontDefault ?: sprites.backDefault
}
