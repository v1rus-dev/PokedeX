package yegor.cheprasov.pokedex.core.network.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val networkEngine: HttpClientEngine = OkHttp.create {
    config { retryOnConnectionFailure(true) }
}
