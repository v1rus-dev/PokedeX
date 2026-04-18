plugins {
    alias(libs.plugins.pokedex.kmp.compose.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.androidx.navigation3.runtime)
        implementation(libs.androidx.lifecycle.viewmodel.navigation3)
        implementation(libs.koin.navigation3)
        implementation(projects.core.resources)
    }
}
