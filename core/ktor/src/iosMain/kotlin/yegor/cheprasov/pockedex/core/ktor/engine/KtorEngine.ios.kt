package yegor.cheprasov.pockedex.core.ktor.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val ktorEngine: HttpClientEngine = Darwin.create()