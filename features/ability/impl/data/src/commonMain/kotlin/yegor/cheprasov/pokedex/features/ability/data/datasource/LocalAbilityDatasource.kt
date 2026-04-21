package yegor.cheprasov.pokedex.features.ability.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.database.TransactionProvider
import yegor.cheprasov.pokedex.core.database.ability.AbilityDao
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityLocalModel

class LocalAbilityDatasource(
    private val transactionProvider: TransactionProvider,
    private val abilityDao: AbilityDao,
) {
    suspend fun getAllAbilities(): Result<List<AbilityEntity>> = withContext(Dispatchers.IO) {
        runCatching {
            abilityDao.getAllAbilities()
        }
    }

    fun observeAllAbilities(): Flow<List<AbilityEntity>> =
        abilityDao.observeAll().flowOn(Dispatchers.IO)

    suspend fun replaceAllAbilities(list: List<AbilityLocalModel>): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            transactionProvider.runAsTransaction {
                abilityDao.clearAllPokemonLinks()
                abilityDao.clearAllAbilities()

                if (list.isNotEmpty()) {
                    abilityDao.upsertAllAbilities(list.map(AbilityLocalModel::ability))

                    val pokemonLinks = list.flatMap(AbilityLocalModel::pokemonLinks)
                    if (pokemonLinks.isNotEmpty()) {
                        abilityDao.upsertPokemonLinks(pokemonLinks)
                    }
                }
            }
        }
    }

    suspend fun upsert(entity: AbilityLocalModel): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            transactionProvider.runAsTransaction {
                abilityDao.upsertAbility(entity.ability)
                abilityDao.deletePokemonLinksByAbilityName(entity.ability.name)

                if (entity.pokemonLinks.isNotEmpty()) {
                    abilityDao.upsertPokemonLinks(entity.pokemonLinks)
                }
            }
        }
    }

    suspend fun hasAbilityInDatabase(): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching { !abilityDao.databaseIsEmpty() }
    }
}
