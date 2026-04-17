package yegor.cheprasov.pokedex.core.ktor.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.ktor.engine.ktorEngine

val ktorModule = module {
    single<HttpClientEngine> { ktorEngine }

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
    }

    single {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(get())
            }

            install(Logging)
            expectSuccess = true
        }
    }
}
