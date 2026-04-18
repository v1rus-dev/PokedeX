package yegor.cheprasov.pokedex.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.io.IOException

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpClient.() -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = block()
        if (response.status.isSuccess()) {
            NetworkResult.Success(response.body<T>())
        } else {
            NetworkResult.HttpError(
                code = response.status.value,
                message = response.bodyAsText()
            )
        }
    } catch (e: IOException) {
        NetworkResult.NetworkError(e)
    } catch (e: Exception) {
        NetworkResult.NetworkError(e)
    }
}
