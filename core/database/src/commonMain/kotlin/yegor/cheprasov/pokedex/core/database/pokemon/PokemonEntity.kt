package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PokemonEntity.TABLE_NAME)
data class PokemonEntity(
    @PrimaryKey
    val id: String
) {
    companion object {
        const val TABLE_NAME = "pokemons"
    }
}
