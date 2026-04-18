package yegor.cheprasov.pokedex.core.database.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {
    @Query("SELECT * FROM favorite_pokemon ORDER BY added_at_epoch_millis DESC")
    fun observeAll(): Flow<List<FavoritePokemonEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FavoritePokemonEntity)

    @Query("DELETE FROM favorite_pokemon WHERE name = :name")
    suspend fun deleteByName(name: String)
}
