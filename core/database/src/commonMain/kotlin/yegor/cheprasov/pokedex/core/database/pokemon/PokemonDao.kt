package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithTypesEntity

@Dao
interface PokemonDao {
    @Query("SELECT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun hasPokemons(): Boolean

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun observeAll(): Flow<List<PokemonWithTypesEntity>>

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getAllPokemons(): List<PokemonWithTypesEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :pokemonName || '%' ORDER BY name ASC")
    fun searchByName(pokemonName: String): Flow<List<PokemonWithTypesEntity>>

    @Query("SELECT * FROM pokemons WHERE is_favorite = 1")
    suspend fun getFavoritePokemons(): List<PokemonEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): PokemonWithTypesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPokemon(entity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllPokemons(entities: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemons()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTypeLinks(entities: List<PokemonTypeCrossRefEntity>)

    @Query("DELETE FROM pokemon_type_links WHERE pokemon_id = :pokemonId")
    suspend fun deleteTypeLinksByPokemonId(pokemonId: Int)

    @Query("DELETE FROM pokemon_type_links")
    suspend fun clearAllTypeLinks()
}
