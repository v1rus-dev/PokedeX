plugins {
    alias(libs.plugins.pokedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(libs.ktor.client.core)
    }
}
