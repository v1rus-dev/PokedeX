plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

dependencies {
    commonMainImplementation(libs.androidx.navigation3.runtime)
}