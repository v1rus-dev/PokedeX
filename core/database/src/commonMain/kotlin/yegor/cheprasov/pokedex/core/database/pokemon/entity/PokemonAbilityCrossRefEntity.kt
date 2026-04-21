package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = PokemonAbilityCrossRefEntity.TABLE_NAME,
    primaryKeys = ["pokemon_name", "ability_name"],
)
data class PokemonAbilityCrossRefEntity(
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
    @ColumnInfo(name = "ability_name")
    val abilityName: String,
    val slot: Int,
    @ColumnInfo(name = "is_hidden")
    val isHidden: Boolean,
) {
    companion object {
        const val TABLE_NAME = "pokemon_ability_links"
    }
}
