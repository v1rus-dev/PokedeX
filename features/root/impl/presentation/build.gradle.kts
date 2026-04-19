plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.home.api)
        implementation(projects.features.favorites.api)
        implementation(projects.features.settings.api)
    }
}
