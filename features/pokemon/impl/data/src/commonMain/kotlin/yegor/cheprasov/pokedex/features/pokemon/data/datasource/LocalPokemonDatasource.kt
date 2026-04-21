package yegor.cheprasov.pokedex.features.pokemon.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.database.TransactionProvider
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonTypeDao
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithTypesEntity
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonLocalModel

class LocalPokemonDatasource(
    private val transactionProvider: TransactionProvider,
    private val pokemonDao: PokemonDao,
    private val pokemonTypeDao: PokemonTypeDao,
) {

    suspend fun hasPokemons(): Result<Boolean> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.hasPokemons()
        }
    }

    suspend fun getPokemonByName(pokemonName: String): Result<PokemonWithTypesEntity?> =
        withContext(Dispatchers.IO) {
            runCatching {
                pokemonDao.getByName(pokemonName.lowercase())
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
            }
        }
    }

    suspend fun upsert(entity: PokemonLocalModel): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            transactionProvider.runAsTransaction {
                pokemonDao.upsertPokemon(entity.pokemon)
                pokemonTypeDao.upsertAll(entity.types)
                pokemonDao.deleteTypeLinksByPokemonId(entity.pokemon.id)

                if (entity.typeLinks.isNotEmpty()) {
                    pokemonDao.upsertTypeLinks(entity.typeLinks)
                }
            }
        }
    }

    suspend fun getFavoritePokemons(): Result<List<PokemonEntity>> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.getFavoritePokemons()
        }
    }

    fun observeAllPokemons(): Flow<List<PokemonWithTypesEntity>> =
        pokemonDao.observeAll().flowOn(Dispatchers.IO)

    fun observePokemonsBySearch(search: String): Flow<List<PokemonWithTypesEntity>> =
        pokemonDao.searchByName(search).flowOn(
            Dispatchers.IO
        )

}
