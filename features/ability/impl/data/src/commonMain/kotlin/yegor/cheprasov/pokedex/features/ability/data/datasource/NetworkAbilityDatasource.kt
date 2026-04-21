package yegor.cheprasov.pokedex.features.ability.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.network.NetworkResult
import yegor.cheprasov.pokedex.core.network.safeRequest
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityListResponse
import yegor.cheprasov.pokedex.features.ability.data.models.AbilityResponse

class NetworkAbilityDatasource(
    private val httpClient: HttpClient,
) {
    suspend fun getAbility(abilityName: String): NetworkResult<AbilityResponse> =
        withContext(Dispatchers.IO) {
            httpClient.safeRequest {
                get("ability/$abilityName") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    suspend fun getAllAbilityList(limit: Int = DEFAULT_LIMIT): NetworkResult<AbilityListResponse> =
        withContext(Dispatchers.IO) {
            httpClient.safeRequest {
                get("ability?limit=$limit") {
                    contentType(ContentType.Application.Json)
                }
            }
        }

    private companion object {
        const val DEFAULT_LIMIT = 500
    }
}
