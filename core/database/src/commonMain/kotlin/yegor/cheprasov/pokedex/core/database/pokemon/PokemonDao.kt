package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonStatEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity

@Dao
interface PokemonDao {
    @Query("SELECT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun hasPokemons(): Boolean

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun observeAll(): Flow<List<PokemonWithRelationsEntity>>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name = :name")
    fun observePokemon(name: String): Flow<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getAllPokemons(): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPokemonsPage(limit: Int, offset: Int): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :pokemonName || '%' ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun searchByNamePage(pokemonName: String, limit: Int, offset: Int): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :pokemonName || '%' ORDER BY name ASC")
    fun searchByName(pokemonName: String): Flow<List<PokemonWithRelationsEntity>>

    @Query("SELECT * FROM pokemons WHERE is_favorite = 1")
    suspend fun getFavoritePokemons(): List<PokemonEntity>

    @Query("UPDATE pokemons SET is_favorite = :isFavorite WHERE name = :pokemonName")
    suspend fun updateFavoriteState(pokemonName: String, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): PokemonWithRelationsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPokemon(entity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllPokemons(entities: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTypeLinks(entities: List<PokemonTypeCrossRefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStats(entities: List<PokemonStatEntity>)

    @Query("DELETE FROM pokemon_type_links WHERE pokemon_name = :pokemonName")
    suspend fun deleteTypeLinksByPokemonName(pokemonName: String)

    @Query("DELETE FROM pokemon_stats WHERE pokemon_name = :pokemonName")
    suspend fun deleteStatsByPokemonName(pokemonName: String)

    @Query("DELETE FROM pokemon_type_links")
    suspend fun clearAllTypeLinks()

    @Query("DELETE FROM pokemon_stats")
    suspend fun clearAllStats()
}
