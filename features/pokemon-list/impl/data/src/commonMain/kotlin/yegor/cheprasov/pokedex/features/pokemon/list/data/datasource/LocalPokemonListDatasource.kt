package yegor.cheprasov.pokedex.features.pokemon.list.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity

class LocalPokemonListDatasource(
    private val pokemonDao: PokemonDao,
) {
    suspend fun getPokemonList(offset: Int, limit: Int): Result<List<PokemonWithRelationsEntity>> = withContext(Dispatchers.IO) {
        runCatching {
            pokemonDao.getPokemonsPage(limit = limit, offset = offset)
        }
    }
}
