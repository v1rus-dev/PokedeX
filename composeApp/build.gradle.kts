import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.multiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

val enableIosTargets = providers.gradleProperty("pokedex.enableIosTargets")
    .map(String::toBoolean)
    .orElse(
        providers.systemProperty("os.name")
            .map { it.startsWith("Mac", ignoreCase = true) }
    )
    .get()

kotlin {
    android {
        namespace = "yegor.cheprasov.pokedex.composeapp"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest {}
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    if (enableIosTargets) {
        listOf(
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation3.runtime)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.core.viewmodel)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.navigation3)
            implementation(libs.napier)

            /** Core dependencies */
            implementation(projects.core.database)
            implementation(projects.core.design)
            implementation(projects.core.network.impl)

            /** Feature dependencies */
            implementation(projects.features.root.impl.presentation)
            implementation(projects.features.home.api)
            implementation(projects.features.home.impl.presentation)
            implementation(projects.features.pokemon.api)
            implementation(projects.features.pokemon.impl.data)
            implementation(projects.features.pokemon.impl.domain)
            implementation(projects.features.pokemonDetails.api)
            implementation(projects.features.pokemonDetails.impl.data)
            implementation(projects.features.pokemonDetails.impl.domain)
            implementation(projects.features.pokemonDetails.impl.presentation)
            implementation(projects.features.favorites.api)
            implementation(projects.features.favorites.impl.data)
            implementation(projects.features.favorites.impl.domain)
            implementation(projects.features.favorites.impl.presentation)
            implementation(projects.features.search.api)
            implementation(projects.features.search.impl.data)
            implementation(projects.features.search.impl.domain)
            implementation(projects.features.search.impl.presentation)
            implementation(projects.features.settings.api)
            implementation(projects.features.settings.impl.data)
            implementation(projects.features.settings.impl.domain)
            implementation(projects.features.settings.impl.presentation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
