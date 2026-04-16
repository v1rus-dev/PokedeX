plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemonList.impl.domain)
    }
}
