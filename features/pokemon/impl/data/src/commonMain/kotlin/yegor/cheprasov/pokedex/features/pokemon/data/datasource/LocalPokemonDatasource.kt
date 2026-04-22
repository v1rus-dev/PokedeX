package yegor.cheprasov.pokedex.features.pokemon.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.database.TransactionProvider
import yegor.cheprasov.pokedex.core.database.ability.AbilityDao
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonTypeDao
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonLocalModel

class LocalPokemonDatasource(
    private val transactionProvider: TransactionProvider,
    private val pokemonDao: PokemonDao,
    private val pokemonTypeDao: PokemonTypeDao,
    private val abilityDao: AbilityDao,
) {

    suspend fun hasPokemons(): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.hasPokemons()
        }
    }

    suspend fun getPokemonByName(pokemonName: String): Result<PokemonWithRelationsEntity?> =
        withContext(Dispatchers.IO) {
            runCatching {
                pokemonDao.getByName(pokemonName.lowercase())
            }
        }

    suspend fun getAllPokemons(): Result<List<PokemonWithRelationsEntity>> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.getAllPokemons()
        }
    }

    suspend fun replaceAllPokemons(list: List<PokemonLocalModel>): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            transactionProvider.runAsTransaction {
                pokemonDao.clearAllTypeLinks()
                pokemonDao.clearAllPokemons()
                pokemonTypeDao.clearAll()

                if (list.isEmpty()) return@runAsTransaction

                pokemonDao.upsertAllPokemons(list.map(PokemonLocalModel::pokemon))

                val distinctTypes = list.flatMap(PokemonLocalModel::types)
                    .distinctBy(PokemonTypeEntity::name)
                pokemonTypeDao.upsertAll(distinctTypes)

                val typeLinks = list.flatMap(PokemonLocalModel::typeLinks)
                if (typeLinks.isNotEmpty()) {
                    pokemonDao.upsertTypeLinks(typeLinks)
                }

                val pokemonNames = list.map { it.pokemon.name }
                if (pokemonNames.isNotEmpty()) {
                    abilityDao.deletePokemonLinksByPokemonNames(pokemonNames)
                }

                val abilityLinks = list.flatMap(PokemonLocalModel::abilityLinks)
                if (abilityLinks.isNotEmpty()) {
                    abilityDao.upsertPokemonLinks(abilityLinks)
                }
            }
        }
    }

    suspend fun upsert(entity: PokemonLocalModel): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            transactionProvider.runAsTransaction {
                pokemonDao.upsertPokemon(entity.pokemon)
                pokemonTypeDao.upsertAll(entity.types)
                pokemonDao.deleteTypeLinksByPokemonName(entity.pokemon.name)
                abilityDao.deletePokemonLinksByPokemonName(entity.pokemon.name)

                if (entity.typeLinks.isNotEmpty()) {
                    pokemonDao.upsertTypeLinks(entity.typeLinks)
                }

                if (entity.abilityLinks.isNotEmpty()) {
                    abilityDao.upsertPokemonLinks(entity.abilityLinks)
                }
            }
        }
    }

    suspend fun getFavoritePokemons(): Result<List<PokemonEntity>> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.getFavoritePokemons()
        }
    }

    fun observeAllPokemons(): Flow<List<PokemonWithRelationsEntity>> =
        pokemonDao.observeAll().flowOn(Dispatchers.IO)

    fun observePokemonsBySearch(search: String): Flow<List<PokemonWithRelationsEntity>> =
        pokemonDao.searchByName(search).flowOn(
            Dispatchers.IO
        )

}
