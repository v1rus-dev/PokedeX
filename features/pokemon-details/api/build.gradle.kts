plugins {
    alias(libs.plugins.pokedex.kmp.feature.api.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.androidx.navigation3.runtime)
        implementation(projects.features.pokemon.api)
    }
}
