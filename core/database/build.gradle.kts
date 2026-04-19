plugins {
    alias(libs.plugins.pokedex.kmp.base.config.plugin)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.ksp)
}

val enableIosTargets = providers.gradleProperty("pokedex.enableIosTargets")
    .map(String::toBoolean)
    .orElse(
        providers.systemProperty("os.name")
            .map { it.startsWith("Mac", ignoreCase = true) }
    )
    .get()

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.androidx.room.runtime)
        implementation(libs.androidx.sqlite.bundled)
        implementation(libs.koin.core)
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    if (enableIosTargets) {
        add("kspIosArm64", libs.androidx.room.compiler)
        add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}
