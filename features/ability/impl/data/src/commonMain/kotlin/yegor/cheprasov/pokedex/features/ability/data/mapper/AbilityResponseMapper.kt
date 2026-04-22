package yegor.cheprasov.pokedex.features.ability.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonAbilityCrossRefEntity
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityLocalModel
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityResponse

class AbilityResponseMapper : Mapper<AbilityResponse, AbilityLocalModel> {
    override fun map(input: AbilityResponse): AbilityLocalModel {
        val normalizedAbilityName = input.name.lowercase()
        val englishEffectEntry = input.effectEntries.firstOrNull { effectEntry ->
            effectEntry.language.name == ENGLISH_LANGUAGE_CODE
        }

        return AbilityLocalModel(
            ability = AbilityEntity(
                id = input.id,
                name = normalizedAbilityName,
                effect = englishEffectEntry?.effect,
                shortEffect = englishEffectEntry?.shortEffect,
            ),
            pokemonLinks = input.pokemon.map { pokemon ->
                PokemonAbilityCrossRefEntity(
                    pokemonName = pokemon.pokemon.name.lowercase(),
                    abilityName = normalizedAbilityName,
                    slot = pokemon.slot,
                    isHidden = pokemon.isHidden,
                )
            },
        )
    }

    private companion object {
        const val ENGLISH_LANGUAGE_CODE = "en"
    }
}
