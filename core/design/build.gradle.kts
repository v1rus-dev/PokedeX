plugins {
    alias(libs.plugins.pokedex.kmp.compose.config.plugin)
}

kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.core.ktx)
    }
    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.androidx.navigation3.runtime)
        implementation(libs.jetbrains.navigation3.ui)
        implementation(libs.androidx.lifecycle.viewmodel.navigation3)
        implementation(libs.koin.navigation3)
        implementation(projects.core.resources)
        implementation(projects.features.settings.api)
    }
}
