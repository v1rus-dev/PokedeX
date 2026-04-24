package yegor.cheprasov.pokedex.core.database.pokemon.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity

data class PokemonWithRelationsEntity(
    @Embedded
    val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "pokemon_name",
    )
    val typeLinks: List<PokemonTypeCrossRefEntity>,
    @Relation(
        parentColumn = "name",
        entityColumn = "pokemon_name",
    )
    val stats: List<PokemonStatEntity>,
    @Relation(
        parentColumn = "name",
        entityColumn = "pokemon_name",
    )
    val abilityLinks: List<PokemonAbilityCrossRefEntity>,
    @Relation(
        entity = AbilityEntity::class,
        parentColumn = "name",
        entityColumn = "name",
        associateBy = Junction(
            value = PokemonAbilityCrossRefEntity::class,
            parentColumn = "pokemon_name",
            entityColumn = "ability_name",
        ),
    )
    val abilities: List<AbilityEntity>,
)
