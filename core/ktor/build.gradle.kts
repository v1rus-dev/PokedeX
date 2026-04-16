plugins {
    alias(libs.plugins.pockedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
        }

        iosMain.dependencies {
            implementation(libs.ktor.engine.darwin)
        }

        androidMain.dependencies {
            implementation(libs.ktor.engine.okhttp)
        }
    }
}