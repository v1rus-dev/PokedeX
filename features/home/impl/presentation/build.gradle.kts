plugins {
    alias(libs.plugins.pokedex.kmp.presentation.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(projects.core.network.api)
        implementation(libs.koin.compose.viewmodel)

        implementation(projects.features.ability.api)
        implementation(projects.features.ability.impl.data)
        implementation(projects.features.pokemon.api)
        implementation(projects.features.home.api)
        implementation(projects.features.home.impl.domain)
        implementation(projects.features.search.api)
    }
}
