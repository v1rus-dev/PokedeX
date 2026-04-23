package yegor.cheprasov.pokedex.features.pokemon.list.data.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonAbilityModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

class PokemonMapper : Mapper<PokemonWithRelationsEntity, PokemonModel> {
    override fun map(input: PokemonWithRelationsEntity): PokemonModel {
        val sortedTypes = input.typeLinks
            .sortedBy { typeLink -> typeLink.slot }
            .map { typeLink -> PokemonType.fromRawNameOrUnknown(typeLink.typeName) }
        val abilityByName = input.abilities.associateBy { ability -> ability.name }
        val sortedAbilities = input.abilityLinks
            .sortedBy { abilityLink -> abilityLink.slot }
            .mapNotNull { abilityLink ->
                abilityByName[abilityLink.abilityName]?.let { ability ->
                    PokemonAbilityModel(
                        ability = ability.toAbilityModel(),
                        isHidden = abilityLink.isHidden,
                    )
                }
            }

        return PokemonModel(
            name = input.pokemon.name,
            isFavorite = input.pokemon.isFavorite,
            height = input.pokemon.height,
            weight = input.pokemon.weight,
            baseExperience = input.pokemon.baseExperience,
            types = sortedTypes,
            abilities = sortedAbilities,
            id = input.pokemon.id,
            sprites = PokemonSprites(
                backDefault = input.pokemon.backDefault,
                frontDefault = input.pokemon.frontDefault,
                backFemale = input.pokemon.backFemale,
                frontFemale = input.pokemon.frontFemale,
            ),
        )
    }

    private fun AbilityEntity.toAbilityModel(): AbilityModel {
        return AbilityModel(
            id = id,
            name = name,
            effect = effect,
            shortEffect = shortEffect,
        )
    }
}
