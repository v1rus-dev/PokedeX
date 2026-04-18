plugins {
    alias(libs.plugins.pokedex.kmp.domain.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(projects.core.network.api)
    }
}
