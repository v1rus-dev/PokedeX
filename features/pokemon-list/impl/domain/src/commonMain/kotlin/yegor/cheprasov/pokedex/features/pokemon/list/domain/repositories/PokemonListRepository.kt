package yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel

interface PokemonListRepository {
    fun getPokemonList(searchQuery: String = "", pageSize: Int): Flow<PagingData<PokemonLiteModel>>
}
