plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.ktor)
        implementation(libs.ktor.client.core)
        implementation(libs.koin.core)
        implementation(projects.features.pokemonList.impl.domain)
    }
}
