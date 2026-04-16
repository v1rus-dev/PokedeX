plugins {
    alias(libs.plugins.pockedex.kmp.base.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.network.api)
    }
}