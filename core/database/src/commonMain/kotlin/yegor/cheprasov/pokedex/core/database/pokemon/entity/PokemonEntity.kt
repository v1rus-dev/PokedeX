package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PokemonEntity.TABLE_NAME)
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "front_default")
    val frontDefault: String?,
    @ColumnInfo(name = "back_default")
    val backDefault: String?,
    @ColumnInfo(name = "front_female")
    val frontFemale: String?,
    @ColumnInfo(name = "back_female")
    val backFemale: String?,
    val height: Int,
    val weight: Int,
    @ColumnInfo(name = "base_experience")
    val baseExperience: Int?,
) {
    companion object {
        const val TABLE_NAME = "pokemons"
    }
}
