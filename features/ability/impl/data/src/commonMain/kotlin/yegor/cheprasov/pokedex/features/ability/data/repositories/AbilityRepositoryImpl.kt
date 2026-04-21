package yegor.cheprasov.pokedex.features.ability.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yegor.cheprasov.pokedex.features.ability.data.datasource.LocalAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityEntityMapper
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityLocalModel
import yegor.cheprasov.pokedex.features.ability.domain.repository.AbilityRepository
import yegor.cheprasov.pokedex.features.ability.models.AbilityModel

class AbilityRepositoryImpl(
    private val localDatasource: LocalAbilityDatasource,
    private val abilityEntityMapper: AbilityEntityMapper,
) : AbilityRepository {
    override suspend fun getAllAbilities(): Result<List<AbilityModel>> = localDatasource.getAllAbilities().map { entities ->
        entities.map(abilityEntityMapper::map)
    }

    override fun observeAllAbilities(): Flow<List<AbilityModel>> =
        localDatasource.observeAllAbilities().map { entities ->
            entities.map(abilityEntityMapper::map)
        }

    override suspend fun replaceAllAbilities(list: List<AbilityModel>): Result<Unit> = localDatasource.replaceAllAbilities(list)

    override suspend fun upsertAbility(entity: AbilityModel): Result<Unit> = localDatasource.upsert(entity)

    override suspend fun hasAbilityInDatabase(): Result<Boolean> = localDatasource.hasAbilityInDatabase()
}
