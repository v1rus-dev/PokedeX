plugins {
    alias(libs.plugins.pockedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        iosMain.dependencies {
            implementation(libs.ktor.engine.darwin)
        }

        androidMain.dependencies {
            implementation(libs.ktor.engine.okhttp)
        }
    }
}