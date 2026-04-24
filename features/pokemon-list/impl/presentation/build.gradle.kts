plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.androidx.paging.compose)
        implementation(libs.androidx.paging.common)

        // Projects
        implementation(projects.features.pokemon.api)
        implementation(projects.features.pokemon.ui)
        implementation(projects.features.pokemonDetails.api)
        implementation(projects.features.pokemonList.impl.domain)
        implementation(projects.features.pokemonList.api)
        implementation(projects.core.common)
    }
}
