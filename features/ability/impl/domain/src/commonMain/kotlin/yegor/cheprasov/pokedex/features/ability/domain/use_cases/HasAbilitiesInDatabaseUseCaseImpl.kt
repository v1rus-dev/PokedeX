package yegor.cheprasov.pokedex.features.ability.domain.use_cases

import yegor.cheprasov.pokedex.features.ability.domain.repository.AbilityRepository
import yegor.cheprasov.pokedex.features.ability.use_cases.HasAbilitiesInDatabaseUseCase

class HasAbilitiesInDatabaseUseCaseImpl(private val abilityRepository: AbilityRepository) : HasAbilitiesInDatabaseUseCase {
    override suspend fun invoke(): Result<Boolean> = abilityRepository.hasAbilityInDatabase()
}