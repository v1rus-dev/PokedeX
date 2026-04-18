package yegor.cheprasov.pokedex.core.network.engine

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val networkEngine: HttpClientEngine = Darwin.create()
