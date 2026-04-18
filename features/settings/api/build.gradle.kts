plugins {
    alias(libs.plugins.pokedex.kmp.feature.api.config.plugin)
}

dependencies {
    commonMainImplementation(libs.androidx.navigation3.runtime)
}
