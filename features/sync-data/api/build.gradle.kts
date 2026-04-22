plugins {
    alias(libs.plugins.pokedex.kmp.feature.api.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
    }
}