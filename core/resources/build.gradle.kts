plugins {
    alias(libs.plugins.pokedex.kmp.compose.config.plugin)
}

kotlin {
    android {
        androidResources {
            enable = true
        }
    }
    sourceSets.commonMain.dependencies {
        api(libs.compose.components.resources)
    }
}

compose.resources {
    publicResClass = true
    generateResClass = auto
}