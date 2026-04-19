plugins {
    alias(libs.plugins.pokedex.kmp.base.config.plugin)
    alias(libs.plugins.buildConfig)
}

buildConfig {
    packageName("yegor.cheprasov.pokedex.core.config")
    useKotlinOutput {
        internalVisibility = true
    }

    buildConfigField("APP_NAME", rootProject.name)
    buildConfigField("APPLICATION_ID", "yegor.cheprasov.pokedex")
    buildConfigField("VERSION_NAME", "1.0")
}
