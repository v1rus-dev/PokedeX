package yegor.cheprasov.pockedex.core.ktor.di

import dev.zacsweers.metro.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import yegor.cheprasov.pockedex.core.ktor.engine.ktorEngine

interface KtorProviders {

    @Provides
    fun providePlatformEngine(): HttpClientEngine = ktorEngine

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    @Provides
    fun provideDefaultClient(
        engine: HttpClientEngine,
        json: Json,
    ): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(json)
        }

        install(Logging)

        expectSuccess = true
    }
}