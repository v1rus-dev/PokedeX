package yegor.cheprasov.pokedex.features.pokemon.list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.features.pokemon.list.data.datasource.LocalPokemonListDatasource
import yegor.cheprasov.pokedex.features.pokemon.list.data.mappers.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
import kotlin.time.Clock

class PokemonPagingSource(
    private val localPokemonListDatasource: LocalPokemonListDatasource,
    private val pokemonMapper: PokemonMapper,
    private val searchQuery: String,
) : PagingSource<Int, PokemonLiteModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonLiteModel> =
        withContext(Dispatchers.IO) {
            val startTime = Clock.System.now().toEpochMilliseconds()
            Napier.v("Start load time", tag = "myTag")
            val offset = params.key ?: DEFAULT_OFFSET
            val limit = params.loadSize

            Napier.v("Load pokemons: limit = $limit, offset = $offset", tag = "myTag")

            return@withContext try {
                val pokemons = loadPokemons(offset = offset, limit = limit)
                    .map(pokemonMapper::map)

                val endTime = Clock.System.now().toEpochMilliseconds()

                Napier.v("Time of loading: ${endTime - startTime}", tag = "myTag")

                LoadResult.Page(
                    data = pokemons,
                    prevKey = previousOffset(offset = offset, limit = limit),
                    nextKey = nextOffset(
                        offset = offset,
                        limit = limit,
                        loadedCount = pokemons.size
                    ),
                )
            } catch (throwable: Throwable) {
                LoadResult.Error(throwable)
            }
        }

    override fun getRefreshKey(state: PagingState<Int, PokemonLiteModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null

        return closestPage.prevKey?.plus(state.config.pageSize)
            ?: closestPage.nextKey?.minus(state.config.pageSize)?.coerceAtLeast(0)
    }

    private fun previousOffset(offset: Int, limit: Int): Int? =
        if (offset == DEFAULT_OFFSET) null else (offset - limit).coerceAtLeast(DEFAULT_OFFSET)

    private fun nextOffset(offset: Int, limit: Int, loadedCount: Int): Int? =
        if (loadedCount < limit) null else offset + loadedCount

    private suspend fun loadPokemons(offset: Int, limit: Int) =
        if (searchQuery.isBlank()) {
            localPokemonListDatasource
                .getPokemonList(offset = offset, limit = limit)
                .getOrThrow()
        } else {
            localPokemonListDatasource
                .searchPokemonList(searchQuery = searchQuery, offset = offset, limit = limit)
                .getOrThrow()
        }

    private companion object {
        const val DEFAULT_OFFSET = 0
    }
}
