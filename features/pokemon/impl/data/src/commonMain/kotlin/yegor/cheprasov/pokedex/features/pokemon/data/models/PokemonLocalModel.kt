package yegor.cheprasov.pokedex.features.pokemon.data.models

import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity

data class PokemonLocalModel(
    val pokemon: PokemonEntity,
    val types: List<PokemonTypeEntity>,
    val typeLinks: List<PokemonTypeCrossRefEntity>,
)
