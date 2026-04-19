plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core.viewmodel)
        implementation(libs.koin.compose.viewmodel)
        implementation(projects.features.home.api)
    }
}
