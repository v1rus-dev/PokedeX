package yegor.cheprasov.pokedex.features.pokemon.domain.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

interface PokemonRepository {
    suspend fun hasPokemons(): Result<Boolean>

    suspend fun getPokemon(pokemonName: String): Result<PokemonModel>

    fun observeAllPokemons(): Flow<List<PokemonModel>>

    suspend fun getAllPokemons(): Result<List<PokemonModel>>

    fun searchPokemonsByName(search: String): Flow<List<PokemonModel>>

    fun syncAllPokemons(): Flow<SyncAllPokemonsState>
}
