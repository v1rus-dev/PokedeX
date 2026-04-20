plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

dependencies {
//    commonMainImplementation(libs.koin.core.viewmodel)
    commonMainImplementation(libs.koin.compose.viewmodel)
    commonMainImplementation(projects.features.settings.api)
    commonMainImplementation(projects.features.settings.impl.domain)
}
