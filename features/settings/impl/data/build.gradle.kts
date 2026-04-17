plugins {
    alias(libs.plugins.pokedex.kmp.feature.base.config.plugin)
}

dependencies {
    commonMainImplementation(projects.features.settings.impl.domain)
}