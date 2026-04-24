plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemonDetails.api)
        implementation(projects.features.pokemonDetails.impl.domain)
        implementation(projects.features.pokemon.api)
        implementation(projects.features.pokemon.ui)
    }
}
