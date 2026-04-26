package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PokemonStatRangeEntity.TABLE_NAME)
data class PokemonStatRangeEntity(
    @PrimaryKey
    @ColumnInfo(name = "stat")
    val stat: PokemonStatDbModel,
    @ColumnInfo(name = "min_value")
    val minValue: Int,
    @ColumnInfo(name = "max_value")
    val maxValue: Int,
) {
    companion object {
        const val TABLE_NAME = "pokemon_stat_ranges"
    }
}
