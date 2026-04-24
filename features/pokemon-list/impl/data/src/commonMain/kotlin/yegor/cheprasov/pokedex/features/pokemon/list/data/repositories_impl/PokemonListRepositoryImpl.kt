package yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.list.data.paging.PokemonPagingSource
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel

class PokemonListRepositoryImpl(
    private val pagingSourceFactory: (String) -> PokemonPagingSource,
) : PokemonListRepository {
    override fun getPokemonList(searchQuery: String, pageSize: Int): Flow<PagingData<PokemonLiteModel>> {
        val normalizedSearchQuery = searchQuery.trim().lowercase()

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                prefetchDistance = pageSize / 2,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { pagingSourceFactory(normalizedSearchQuery) },
        ).flow
    }
}
