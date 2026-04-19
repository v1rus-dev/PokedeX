package yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface GetPokemonListUseCase {
    operator fun invoke(pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<PokemonModel>>

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}

class GetPokemonListUseCaseImpl(
    private val pokemonListRepository: PokemonListRepository,
) : GetPokemonListUseCase {
    override fun invoke(pageSize: Int): Flow<PagingData<PokemonModel>> {
        return pokemonListRepository.getPokemonList(pageSize = pageSize)
    }
}
