package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonAbilityCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonLocalModel
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonResponse

class PokemonResponseMapper : Mapper<PokemonResponse, PokemonLocalModel> {
    override fun map(input: PokemonResponse): PokemonLocalModel {
        return map(
            input = input,
            isFavorite = false,
        )
    }

    fun map(
        input: PokemonResponse,
        isFavorite: Boolean,
    ): PokemonLocalModel {
        val normalizedPokemonName = input.name.lowercase()
        val sortedTypes = input.types.sortedBy { it.slot }
        val sortedAbilities = input.abilities.sortedBy { it.slot }
        val pokemonEntity = PokemonEntity(
            name = normalizedPokemonName,
            id = input.id,
            isFavorite = isFavorite,
            frontDefault = input.sprites.frontDefault,
            backDefault = input.sprites.backDefault,
            frontFemale = input.sprites.frontFemale,
            backFemale = input.sprites.backFemale,
            height = input.height,
            weight = input.weight,
            baseExperience = input.baseExperience,
        )
        val types = sortedTypes.map { typeSlot ->
            PokemonTypeEntity(name = typeSlot.type.name.lowercase())
        }
        val typeLinks = sortedTypes.map { typeSlot ->
            PokemonTypeCrossRefEntity(
                pokemonName = normalizedPokemonName,
                typeName = typeSlot.type.name.lowercase(),
                slot = typeSlot.slot,
            )
        }
        val abilityLinks = sortedAbilities.map { abilitySlot ->
            PokemonAbilityCrossRefEntity(
                pokemonName = normalizedPokemonName,
                abilityName = abilitySlot.ability.name.lowercase(),
                slot = abilitySlot.slot,
                isHidden = abilitySlot.isHidden,
            )
        }

        return PokemonLocalModel(
            pokemon = pokemonEntity,
            types = types,
            typeLinks = typeLinks,
            abilityLinks = abilityLinks,
        )
    }
}
