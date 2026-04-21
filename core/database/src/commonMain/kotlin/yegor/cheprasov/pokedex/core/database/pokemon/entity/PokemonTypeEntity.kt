package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PokemonTypeEntity.TABLE_NAME)
data class PokemonTypeEntity(
    @PrimaryKey
    val name: String,
) {
    companion object {
        const val TABLE_NAME = "pokemon_types"
    }
}
