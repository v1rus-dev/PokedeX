plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemonDetails.impl.domain)
    }
}