package yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface PokemonListRepository {
    fun getPokemonList(pageSize: Int): Flow<PagingData<PokemonModel>>
}
