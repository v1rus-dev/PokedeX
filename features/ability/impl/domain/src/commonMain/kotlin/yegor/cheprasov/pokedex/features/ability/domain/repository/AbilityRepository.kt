package yegor.cheprasov.pokedex.features.ability.domain.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel

interface AbilityRepository {
    suspend fun getAllAbilities(): Result<List<AbilityModel>>

    fun observeAllAbilities(): Flow<List<AbilityModel>>

    suspend fun replaceAllAbilities(list: List<AbilityModel>): Result<Unit>

    suspend fun upsertAbility(entity: AbilityModel): Result<Unit>

    suspend fun hasAbilityInDatabase(): Result<Boolean>
}