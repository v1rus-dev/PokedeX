package yegor.cheprasov.pokedex.features.pokemon.models

data class PokemonModel(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
) {
    val imageUrl: String? = sprites.frontDefault ?: sprites.backDefault
}
