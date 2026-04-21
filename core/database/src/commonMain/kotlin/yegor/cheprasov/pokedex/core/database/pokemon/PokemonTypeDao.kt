package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity

@Dao
interface PokemonTypeDao {

    @Query("SELECT * FROM pokemon_types ORDER BY name ASC")
    fun observeAll(): Flow<List<PokemonTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(pokemonTypes: List<PokemonTypeEntity>)

    @Query("DELETE FROM pokemon_types")
    suspend fun clearAll()
}
