package yegor.cheprasov.pokedex.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.network.engine.networkEngine

val networkModule = module {
    single<HttpClientEngine> { networkEngine }

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

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "Ktor") { message }
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                url("https://pokeapi.co/api/v2/")
            }

            expectSuccess = true
        }
    }
}
