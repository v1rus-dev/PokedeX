package yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories

import yegor.cheprasov.pokedex.core.network.NetworkResult

interface PokemonListRepository {
    suspend fun getPokemonList(): NetworkResult<Unit>
}
