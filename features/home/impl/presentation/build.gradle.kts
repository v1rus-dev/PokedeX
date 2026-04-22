plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(libs.koin.compose.viewmodel)

        implementation(projects.features.pokemon.api)
        implementation(projects.features.home.api)
        implementation(projects.features.home.impl.domain)
        implementation(projects.features.syncData.api)
        implementation(projects.features.search.api)
    }
}
