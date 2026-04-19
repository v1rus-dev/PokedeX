package yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import yegor.cheprasov.pokedex.features.pokemon.list.data.paging.PokemonListPagingSource
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

class PokemonListRepositoryImpl(
    private val pagingSourceFactory: () -> PokemonListPagingSource,
) : PokemonListRepository {
    override fun getPokemonList(pageSize: Int): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                prefetchDistance = pageSize / 2,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}
