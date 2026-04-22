plugins {
    alias(libs.plugins.pokedex.kmp.domain.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.syncData.api)
        implementation(projects.features.pokemon.api)
    }
}
