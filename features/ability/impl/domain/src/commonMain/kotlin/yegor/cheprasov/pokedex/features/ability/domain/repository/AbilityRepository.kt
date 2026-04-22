package yegor.cheprasov.pokedex.features.ability.domain.repository

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel
import yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState

interface AbilityRepository {
    suspend fun getAllAbilities(): Result<List<AbilityModel>>

    fun observeAllAbilities(): Flow<List<AbilityModel>>

    suspend fun hasAbilityInDatabase(): Result<Boolean>

    fun syncAllAbilities(): Flow<SyncAllAbilitiesState>
}
