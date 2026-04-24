plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

dependencies {
    commonMainImplementation(projects.features.settings.api)
    commonMainImplementation(projects.features.settings.impl.domain)
}
