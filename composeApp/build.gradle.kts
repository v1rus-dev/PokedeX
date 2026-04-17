import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation3.runtime)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.compose)
            implementation(libs.koin.navigation3)

            /** Core dependencies */
            implementation(projects.core.database)
            implementation(projects.core.design)
            implementation(projects.core.ktor)
            implementation(projects.core.network.api)
            implementation(projects.core.network.impl)

            /** Feature dependencies */
            implementation(projects.features.root.impl.presentation)
            implementation(projects.features.pokemon.api)
            implementation(projects.features.pokemonList.api)
            implementation(projects.features.pokemonList.impl.data)
            implementation(projects.features.pokemonList.impl.domain)
            implementation(projects.features.pokemonList.impl.presentation)
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

android {
    namespace = "yegor.cheprasov.pokedex"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "yegor.cheprasov.pokedex"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
