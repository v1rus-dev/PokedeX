package yegor.cheprasov.pokedex.features.pokemon.models

data class PokemonModel(
    val name: String,
    val isFavorite: Boolean,
    val height: Int,
    val weight: Int,
    val baseExperience: Int?,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbilityModel>,
    val sprites: PokemonSprites,
) {
    val imageUrl: String? = sprites.frontDefault ?: sprites.backDefault
}
