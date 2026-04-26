package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonStatWithRangeEntity(
    @Embedded
    val stat: PokemonStatEntity,
    @Relation(
        parentColumn = "stat",
        entityColumn = "stat",
    )
    val range: PokemonStatRangeEntity?,
)
