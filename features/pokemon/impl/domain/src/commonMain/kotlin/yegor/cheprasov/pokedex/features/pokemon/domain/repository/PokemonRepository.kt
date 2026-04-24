package yegor.cheprasov.pokedex.features.pokemon.domain.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

interface PokemonRepository {
    suspend fun hasPokemons(): Result<Boolean>

    suspend fun getPokemon(pokemonName: String): Result<PokemonModel>

    fun observeAllPokemons(): Flow<List<PokemonLiteModel>>

    suspend fun getAllPokemons(): Result<List<PokemonLiteModel>>

    fun searchPokemonsByName(search: String): Flow<List<PokemonLiteModel>>

    fun syncAllPokemons(): Flow<SyncAllPokemonsState>

    fun observePokemon(pokemonName: String): Flow<PokemonModel>

    fun observePokemonIsFavorite(pokemonName: String): Flow<Boolean>

    suspend fun updateFavoriteState(pokemonName: String, isFavorite: Boolean): Result<Unit>
}
