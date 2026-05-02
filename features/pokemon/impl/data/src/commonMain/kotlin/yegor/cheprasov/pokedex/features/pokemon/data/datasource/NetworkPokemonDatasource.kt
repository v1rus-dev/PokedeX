package yegor.cheprasov.pokedex.features.pokemon.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.network.NetworkResult
import yegor.cheprasov.pokedex.core.network.safeRequest
import yegor.cheprasov.pokedex.features.pokemon.data.models.EvolutionChainListResponse
import yegor.cheprasov.pokedex.features.pokemon.data.models.EvolutionChainResponse
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonListResponse
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonResponse

class NetworkPokemonDatasource(
    private val httpClient: HttpClient
) {

    suspend fun getPokemon(pokemonName: String): NetworkResult<PokemonResponse> =
        withContext(Dispatchers.IO) {
            httpClient.safeRequest {
                get("pokemon/$pokemonName") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    suspend fun getAllPokemonList(limit: Int = 10_000): NetworkResult<PokemonListResponse> =
        withContext(
            Dispatchers.IO
        ) {
            httpClient.safeRequest {
                get("pokemon?limit=$limit") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    suspend fun getAllEvolutionChainList(limit: Int = EVOLUTION_CHAIN_LIMIT): NetworkResult<EvolutionChainListResponse> =
        withContext(Dispatchers.IO) {
            httpClient.safeRequest {
                get("evolution-chain?limit=$limit") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    suspend fun getEvolutionChain(id: Int): NetworkResult<EvolutionChainResponse> =
        withContext(Dispatchers.IO) {
            httpClient.safeRequest {
                get("evolution-chain/$id/") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    private companion object {
        const val EVOLUTION_CHAIN_LIMIT = 600
    }

}
