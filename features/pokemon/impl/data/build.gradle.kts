plugins {
    alias(libs.plugins.pokedex.kmp.data.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemon.api)
        implementation(projects.features.pokemon.impl.domain)
    }
}
