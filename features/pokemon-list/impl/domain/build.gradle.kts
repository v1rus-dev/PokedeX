plugins {
    alias(libs.plugins.pokedex.kmp.domain.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemon.api)
        implementation(libs.androidx.paging.common)
    }
}
