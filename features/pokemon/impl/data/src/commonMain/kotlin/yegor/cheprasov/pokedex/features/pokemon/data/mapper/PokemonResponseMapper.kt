package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonEntity
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonResponse

class PokemonResponseMapper : Mapper<PokemonResponse, PokemonEntity> {
    override fun map(input: PokemonResponse): PokemonEntity {
        return map(
            input = input,
            isFavorite = false,
        )
    }

    fun map(
        input: PokemonResponse,
        isFavorite: Boolean,
    ): PokemonEntity {
        val sortedTypes = input.types.sortedBy { it.slot }

        return PokemonEntity(
            id = input.id,
            name = input.name,
            isFavorite = isFavorite,
            frontDefault = input.sprites.frontDefault,
            backDefault = input.sprites.backDefault,
            frontFemale = input.sprites.frontFemale,
            backFemale = input.sprites.backFemale,
            height = input.height,
            weight = input.weight,
            baseExperience = input.baseExperience,
            primaryType = sortedTypes.firstOrNull()?.type?.name.orEmpty(),
            secondaryType = sortedTypes.getOrNull(1)?.type?.name,
        )
    }
}
