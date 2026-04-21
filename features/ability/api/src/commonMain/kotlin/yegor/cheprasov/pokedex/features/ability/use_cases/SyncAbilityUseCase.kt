package yegor.cheprasov.pokedex.features.ability.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.ability.models.SyncAllAbilitiesState

interface SyncAbilityUseCase {
    operator fun invoke(): Flow<SyncAllAbilitiesState>
}