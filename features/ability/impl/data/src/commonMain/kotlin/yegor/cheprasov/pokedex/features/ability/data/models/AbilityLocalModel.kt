package yegor.cheprasov.pokedex.features.ability.data.models

import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonAbilityCrossRefEntity

data class AbilityLocalModel(
    val ability: AbilityEntity,
    val pokemonLinks: List<PokemonAbilityCrossRefEntity>,
)
