plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemonDetails.impl.domain)
        implementation(projects.features.pokemonDetails.api)
        implementation(libs.koin.core.viewmodel)
        implementation(libs.koin.compose.viewmodel)
    }
}
