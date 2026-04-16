plugins {
    alias(libs.plugins.pokedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.network.api)
    }
}