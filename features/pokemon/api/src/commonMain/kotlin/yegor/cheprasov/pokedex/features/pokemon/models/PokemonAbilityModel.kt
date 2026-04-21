package yegor.cheprasov.pokedex.features.pokemon.models

import yegor.cheprasov.pokedex.features.ability.models.AbilityModel

data class PokemonAbilityModel(
    val ability: AbilityModel,
    val slot: Int,
    val isHidden: Boolean,
)
