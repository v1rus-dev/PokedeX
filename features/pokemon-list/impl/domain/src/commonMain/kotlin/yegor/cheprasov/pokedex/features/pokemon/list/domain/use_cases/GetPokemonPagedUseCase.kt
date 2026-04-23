package yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface GetPokemonPagedUseCase {
    operator fun invoke(
        searchQuery: String = "",
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Flow<PagingData<PokemonModel>>

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}

class GetPokemonPagedUseCaseImpl(
    private val pokemonListRepository: PokemonListRepository,
) : GetPokemonPagedUseCase {
    override fun invoke(searchQuery: String, pageSize: Int): Flow<PagingData<PokemonModel>> {
        return pokemonListRepository.getPokemonList(searchQuery, pageSize)
    }
}
