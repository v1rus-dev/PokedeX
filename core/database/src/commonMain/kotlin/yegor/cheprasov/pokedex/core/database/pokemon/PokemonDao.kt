package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun hasPokemons(): Boolean

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun observeAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(entities: List<PokemonEntity>) {
        clearAll()
        upsertAll(entities)
    }
}
