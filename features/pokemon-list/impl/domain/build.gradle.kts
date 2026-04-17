plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
    }
}
