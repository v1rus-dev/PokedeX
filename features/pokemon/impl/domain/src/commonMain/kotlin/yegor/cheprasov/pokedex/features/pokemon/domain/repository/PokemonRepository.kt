package yegor.cheprasov.pokedex.features.pokemon.domain.repository

interface PokemonRepository {
    suspend fun getPokemon(pokemonName: String)
}