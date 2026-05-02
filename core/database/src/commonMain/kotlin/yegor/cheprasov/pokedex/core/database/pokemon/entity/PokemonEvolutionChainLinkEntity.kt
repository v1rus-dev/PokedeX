package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = PokemonEvolutionChainLinkEntity.TABLE_NAME,
    primaryKeys = ["chain_id", "pokemon_name"],
    indices = [
        Index(value = ["pokemon_name"]),
        Index(value = ["chain_id", "slot"], unique = true),
    ],
)
data class PokemonEvolutionChainLinkEntity(
    @ColumnInfo(name = "chain_id")
    val chainId: Int,
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
    val slot: Int,
) {
    companion object {
        const val TABLE_NAME = "pokemon_evolution_chain_links"
    }
}
