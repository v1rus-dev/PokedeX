plugins {
    alias(libs.plugins.pokedex.kmp.feature.api.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.syncData.api)
        implementation(libs.kotlinx.coroutines.core)
    }
}