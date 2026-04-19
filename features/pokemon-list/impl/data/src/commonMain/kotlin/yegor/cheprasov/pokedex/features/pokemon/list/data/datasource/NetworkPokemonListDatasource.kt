package yegor.cheprasov.pokedex.features.pokemon.list.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.core.network.safeRequest
import yegor.cheprasov.pokedex.features.pokemon.list.data.models.PokemonListResponse

class NetworkPokemonListDatasource(
    private val httpClient: HttpClient
) {

    suspend fun getPokemonList(offset: Int, limit: Int): Result<PokemonListResponse> = withContext(
        Dispatchers.IO
    ) {
        return@withContext httpClient.safeRequest<PokemonListResponse> {
            get("pokemon") {
                contentType(ContentType.Application.Json)
                parameter("offset", offset)
                parameter("limit", limit)
            }
        }
            .asResult()
    }

}
