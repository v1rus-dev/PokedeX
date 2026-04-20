package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonEntity
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites

class PokemonEntityMapper : Mapper<PokemonEntity, PokemonModel> {
    override fun map(input: PokemonEntity): PokemonModel {
        return PokemonModel(
            id = input.id,
            name = input.name,
            isFavorite = input.isFavorite,
            height = input.height,
            weight = input.weight,
            baseExperience = input.baseExperience,
            primaryType = input.primaryType,
            secondaryType = input.secondaryType,
            sprites = PokemonSprites(
                backDefault = input.backDefault,
                frontDefault = input.frontDefault,
                backFemale = input.backFemale,
                frontFemale = input.frontFemale,
            ),
        )
    }
}
