plugins {
    alias(libs.plugins.pokedex.kmp.data.config.plugin)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(projects.features.ability.api)
        implementation(projects.features.ability.impl.data)
        implementation(projects.features.pokemon.api)
        implementation(projects.features.pokemon.impl.domain)
    }
}
