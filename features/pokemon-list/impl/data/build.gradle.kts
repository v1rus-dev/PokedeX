plugins {
    alias(libs.plugins.pokedex.kmp.data.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemon.api)
        implementation(projects.features.pokemonList.impl.domain)
        implementation(libs.androidx.paging.common)
    }
}
