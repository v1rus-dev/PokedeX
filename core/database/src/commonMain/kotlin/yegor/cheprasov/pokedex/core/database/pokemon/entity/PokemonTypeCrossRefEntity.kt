package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonTypeCrossRefEntity.TABLE_NAME,
    primaryKeys = ["pokemon_id", "type_name"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemon_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = PokemonTypeEntity::class,
            parentColumns = ["name"],
            childColumns = ["type_name"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["pokemon_id"]),
        Index(value = ["type_name"]),
        Index(value = ["pokemon_id", "slot"], unique = true),
    ],
)
data class PokemonTypeCrossRefEntity(
    @ColumnInfo(name = "pokemon_id")
    val pokemonId: Int,
    @ColumnInfo(name = "type_name")
    val typeName: String,
    val slot: Int,
) {
    companion object {
        const val TABLE_NAME = "pokemon_type_links"
    }
}
