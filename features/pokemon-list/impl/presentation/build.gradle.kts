plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core.viewmodel)
        implementation(libs.koin.compose.viewmodel)
        implementation(projects.features.pokemonDetails.api)
        implementation(projects.features.pokemonList.impl.domain)
        implementation(projects.features.pokemonList.api)
        implementation(libs.androidx.paging.compose)
        implementation(libs.androidx.paging.common)
    }
}
