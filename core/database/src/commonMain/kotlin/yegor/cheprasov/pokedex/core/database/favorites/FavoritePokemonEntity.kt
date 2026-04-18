package yegor.cheprasov.pokedex.core.database.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemonEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "added_at_epoch_millis")
    val addedAtEpochMillis: Long,
)
