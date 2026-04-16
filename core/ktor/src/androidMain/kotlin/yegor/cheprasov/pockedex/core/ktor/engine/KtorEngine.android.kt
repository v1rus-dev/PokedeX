package yegor.cheprasov.pockedex.core.ktor.engine

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual val ktorEngine: HttpClientEngine = OkHttp.create {
    config { retryOnConnectionFailure(true) }
}