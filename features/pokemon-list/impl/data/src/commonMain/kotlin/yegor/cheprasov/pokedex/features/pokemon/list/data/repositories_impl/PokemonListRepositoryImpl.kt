package yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl

import io.ktor.client.HttpClient
import yegor.cheprasov.pokedex.core.network.NetworkResult
import yegor.cheprasov.pokedex.core.network.safeRequest
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

class PokemonListRepositoryImpl(
    private val httpClient: HttpClient,
) : PokemonListRepository {
    override suspend fun getPokemonList(): NetworkResult<Unit> = httpClient.safeRequest {
        TODO("Implement Pokemon list request")
    }
}
