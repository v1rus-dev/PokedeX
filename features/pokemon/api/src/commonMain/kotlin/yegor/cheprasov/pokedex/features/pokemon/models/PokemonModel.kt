package yegor.cheprasov.pokedex.features.pokemon.models

data class PokemonModel(
    val name: String,
    val id: Int,
    val isFavorite: Boolean,
    val height: Int,
    val weight: Int,
    val baseExperience: Int?,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbilityModel>,
    val stats: List<PokemonStatModel>,
    val sprites: PokemonSprites,
) {
    val imageUrl: String? = sprites.frontDefault ?: sprites.backDefault
}

data class PokemonLiteModel(
    val name: String,
    val id: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites,
) {
    val imageUrl: String? = sprites.frontDefault ?: sprites.backDefault
}
