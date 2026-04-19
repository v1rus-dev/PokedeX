package yegor.cheprasov.pokedex.features.pokemon.list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.http.Url
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import yegor.cheprasov.pokedex.features.pokemon.list.data.datasource.NetworkPokemonListDatasource
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase

class PokemonListPagingSource(
    private val networkPokemonListDatasource: NetworkPokemonListDatasource,
    private val getPokemonUseCase: GetPokemonUseCase,
) : PagingSource<Int, PokemonModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val offset = params.key ?: DEFAULT_OFFSET
        val limit = params.loadSize

        return try {
            val response = networkPokemonListDatasource
                .getPokemonList(offset = offset, limit = limit)
                .getOrThrow()

            val pokemons = coroutineScope {
                response.results.map { pokemonListItem ->
                    async {
                        getPokemonUseCase(pokemonListItem.name).getOrThrow()
                    }
                }.awaitAll()
            }

            LoadResult.Page(
                data = pokemons,
                prevKey = extractOffset(response.previous),
                nextKey = extractOffset(response.next),
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null

        return closestPage.prevKey?.plus(state.config.pageSize)
            ?: closestPage.nextKey?.minus(state.config.pageSize)?.coerceAtLeast(0)
    }

    private fun extractOffset(url: String?): Int? {
        if (url == null) return null
        return Url(url).parameters["offset"]?.toIntOrNull()
    }

    private companion object {
        const val DEFAULT_OFFSET = 0
    }
}
