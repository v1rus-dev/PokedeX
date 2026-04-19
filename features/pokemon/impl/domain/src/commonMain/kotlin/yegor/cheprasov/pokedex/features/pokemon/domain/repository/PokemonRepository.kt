package yegor.cheprasov.pokedex.features.pokemon.domain.repository

import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface PokemonRepository {
    suspend fun getPokemon(pokemonName: String): Result<PokemonModel>
}