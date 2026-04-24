package yegor.cheprasov.pokedex.features.pokemon.list.data.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

class PokemonMapper : Mapper<PokemonWithRelationsEntity, PokemonLiteModel> {
    override fun map(input: PokemonWithRelationsEntity): PokemonLiteModel {
        val sortedTypes = input.typeLinks
            .sortedBy { typeLink -> typeLink.slot }
            .map { typeLink -> PokemonType.fromRawNameOrUnknown(typeLink.typeName) }

        return PokemonLiteModel(
            name = input.pokemon.name,
            types = sortedTypes,
            id = input.pokemon.id,
            sprites = PokemonSprites(
                backDefault = input.pokemon.backDefault,
                frontDefault = input.pokemon.frontDefault,
                backFemale = input.pokemon.backFemale,
                frontFemale = input.pokemon.frontFemale,
            ),
        )
    }
}
