plugins {
    alias(libs.plugins.pokedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network.api)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
        }

        iosMain.dependencies {
            implementation(libs.ktor.engine.darwin)
        }

        androidMain.dependencies {
            implementation(libs.ktor.engine.okhttp)
        }
    }
}
