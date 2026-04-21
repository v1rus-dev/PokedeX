package yegor.cheprasov.pokedex.features.ability.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel

class AbilityEntityMapper : Mapper<AbilityEntity, AbilityModel> {
    override fun map(input: AbilityEntity): AbilityModel {
        return AbilityModel(
            id = input.id,
            name = input.name,
            effect = input.effect,
            shortEffect = input.shortEffect,
        )
    }
}
