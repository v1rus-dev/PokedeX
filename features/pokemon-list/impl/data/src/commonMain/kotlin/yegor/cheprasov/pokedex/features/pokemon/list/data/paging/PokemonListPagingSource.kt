package yegor.cheprasov.pokedex.features.pokemon.list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import yegor.cheprasov.pokedex.features.pokemon.list.data.datasource.LocalPokemonListDatasource
import yegor.cheprasov.pokedex.features.pokemon.list.data.mappers.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

class PokemonListPagingSource(
    private val localPokemonListDatasource: LocalPokemonListDatasource,
    private val pokemonMapper: PokemonMapper,
) : PagingSource<Int, PokemonModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val offset = params.key ?: DEFAULT_OFFSET
        val limit = params.loadSize

        return try {
            val pokemons = localPokemonListDatasource
                .getPokemonList(offset = offset, limit = limit)
                .getOrThrow()
                .map(pokemonMapper::map)

            LoadResult.Page(
                data = pokemons,
                prevKey = previousOffset(offset = offset, limit = limit),
                nextKey = nextOffset(offset = offset, limit = limit, loadedCount = pokemons.size),
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

    private fun previousOffset(offset: Int, limit: Int): Int? =
        if (offset == DEFAULT_OFFSET) null else (offset - limit).coerceAtLeast(DEFAULT_OFFSET)

    private fun nextOffset(offset: Int, limit: Int, loadedCount: Int): Int? =
        if (loadedCount < limit) null else offset + loadedCount

    private companion object {
        const val DEFAULT_OFFSET = 0
    }
}
