plugins {
    alias(libs.plugins.pokedex.kmp.domain.config.plugin)
}

kotlin {
    kotlin.sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemon.api)
    }
}