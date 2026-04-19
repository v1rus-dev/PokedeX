plugins {
    alias(libs.plugins.pokedex.kmp.data.config.plugin)
}

dependencies {
    commonMainImplementation(projects.features.settings.api)
    commonMainImplementation(projects.features.settings.impl.domain)
    commonTestImplementation(libs.kotlin.test)
}
