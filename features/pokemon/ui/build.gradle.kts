plugins {
    alias(libs.plugins.pokedex.kmp.ui.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.features.pokemon.api)
    }
    sourceSets.commonTest.dependencies {
        implementation(libs.kotlin.test)
    }
}
