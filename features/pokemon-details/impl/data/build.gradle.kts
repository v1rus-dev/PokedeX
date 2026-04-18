plugins {
    alias(libs.plugins.pokedex.kmp.data.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemonDetails.impl.domain)
    }
}
