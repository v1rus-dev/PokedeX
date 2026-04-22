package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = PokemonTypeCrossRefEntity.TABLE_NAME,
    primaryKeys = ["pokemon_name", "type_name"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["name"],
            childColumns = ["pokemon_name"],
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
        Index(value = ["pokemon_name"]),
        Index(value = ["type_name"]),
        Index(value = ["pokemon_name", "slot"], unique = true),
    ],
)
data class PokemonTypeCrossRefEntity(
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
    @ColumnInfo(name = "type_name")
    val typeName: String,
    val slot: Int,
) {
    companion object {
        const val TABLE_NAME = "pokemon_type_links"
    }
}
