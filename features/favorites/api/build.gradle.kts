plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.androidx.navigation3.runtime)
    }
}
