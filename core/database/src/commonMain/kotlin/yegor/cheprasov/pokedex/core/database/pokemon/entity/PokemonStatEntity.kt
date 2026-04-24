package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonStatEntity.TABLE_NAME,
    primaryKeys = ["pokemon_name", "stat"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["name"],
            childColumns = ["pokemon_name"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["pokemon_name"]),
    ],
)
data class PokemonStatEntity(
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
    @ColumnInfo(name = "stat")
    val stat: PokemonStatDbModel,
    @ColumnInfo(name = "stat_value")
    val statValue: Int,
) {
    companion object {
        const val TABLE_NAME = "pokemon_stats"
    }
}
