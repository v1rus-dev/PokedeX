package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonStatDbModel
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonAbilityModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonStatModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonStats
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

class PokemonEntityMapper : Mapper<PokemonWithRelationsEntity, PokemonModel> {
    override fun map(input: PokemonWithRelationsEntity): PokemonModel {
        val sortedTypes = mapTypes(input)
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
        val sortedStats = input.stats
            .map { statEntity ->
                PokemonStatModel(
                    stat = statEntity.stat.toDomainModel(),
                    statValue = statEntity.statValue,
                )
            }
            .sortedBy { statModel -> statModel.stat.ordinal }

        return PokemonModel(
            name = input.pokemon.name,
            isFavorite = input.pokemon.isFavorite,
            height = input.pokemon.height,
            weight = input.pokemon.weight,
            baseExperience = input.pokemon.baseExperience,
            types = sortedTypes,
            abilities = sortedAbilities,
            stats = sortedStats,
            id = input.pokemon.id,
            sprites = mapSprites(input),
        )
    }

    fun mapLite(input: PokemonWithRelationsEntity): PokemonLiteModel {
        return PokemonLiteModel(
            name = input.pokemon.name,
            id = input.pokemon.id,
            types = mapTypes(input),
            sprites = mapSprites(input),
        )
    }

    private fun mapTypes(input: PokemonWithRelationsEntity): List<PokemonType> {
        return input.typeLinks
            .sortedBy { typeLink -> typeLink.slot }
            .map { typeLink -> PokemonType.fromRawNameOrUnknown(typeLink.typeName) }
    }

    private fun mapSprites(input: PokemonWithRelationsEntity): PokemonSprites {
        return PokemonSprites(
            backDefault = input.pokemon.backDefault,
            frontDefault = input.pokemon.frontDefault,
            backFemale = input.pokemon.backFemale,
            frontFemale = input.pokemon.frontFemale,
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

    private fun PokemonStatDbModel.toDomainModel(): PokemonStats {
        return PokemonStats.entries.firstOrNull { stat ->
            stat.name == name
        } ?: PokemonStats.Unknown
    }
}
