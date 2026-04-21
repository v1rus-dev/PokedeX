package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonWithTypesEntity(
    @Embedded
    val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemon_id",
    )
    val typeLinks: List<PokemonTypeCrossRefEntity>,
)
