package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithTypesEntity
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

class PokemonEntityMapper : Mapper<PokemonWithTypesEntity, PokemonModel> {
    override fun map(input: PokemonWithTypesEntity): PokemonModel {
        val sortedTypes = input.typeLinks
            .sortedBy { typeLink -> typeLink.slot }
            .map { typeLink -> PokemonType.fromRawNameOrUnknown(typeLink.typeName) }

        return PokemonModel(
            id = input.pokemon.id,
            name = input.pokemon.name,
            isFavorite = input.pokemon.isFavorite,
            height = input.pokemon.height,
            weight = input.pokemon.weight,
            baseExperience = input.pokemon.baseExperience,
            types = sortedTypes,
            sprites = PokemonSprites(
                backDefault = input.pokemon.backDefault,
                frontDefault = input.pokemon.frontDefault,
                backFemale = input.pokemon.backFemale,
                frontFemale = input.pokemon.frontFemale,
            ),
        )
    }
}
