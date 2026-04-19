plugins {
    alias(libs.plugins.pokedex.kmp.domain.config.plugin)
}

dependencies {
    commonMainImplementation(projects.features.settings.api)
}
