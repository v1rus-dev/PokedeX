import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "yegor.cheprasov.pokedex.buildlogic.convention"

dependencies {
    implementation(libs.gradleplugin.android)
    implementation(libs.gradleplugin.kotlin)
    implementation(libs.gradleplugin.kotlinSerialization)
    implementation(libs.gradleplugin.compose)
    implementation(libs.gradleplugin.composeCompiler)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.javaConvention.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}

gradlePlugin {
    plugins {
        register("pokedex.kmp.compose.base.config.plugin") {
            id = "pokedex.kmp.compose.base.config.plugin"
            implementationClass = "KmpComposeBaseConfigPlugin"
        }
        register("pokedex.kmp.base.config.plugin") {
            id = "pokedex.kmp.base.config.plugin"
            implementationClass = "KmpBaseConfigPlugin"
        }
        register("pokedex.kmp.compose.config.plugin") {
            id = "pokedex.kmp.compose.config.plugin"
            implementationClass = "KmpComposeConfigPlugin"
        }
        register("pokedex.kmp.ui.config.plugin") {
            id = "pokedex.kmp.ui.config.plugin"
            implementationClass = "KmpUiConfigPlugin"
        }
        register("pokedex.kmp.presentation.config.plugin") {
            id = "pokedex.kmp.presentation.config.plugin"
            implementationClass = "KmpPresentationConfigPlugin"
        }
        register("pokedex.kmp.data.config.plugin") {
            id = "pokedex.kmp.data.config.plugin"
            implementationClass = "KmpDataConfigPlugin"
        }
        register("pokedex.kmp.domain.config.plugin") {
            id = "pokedex.kmp.domain.config.plugin"
            implementationClass = "KmpDomainConfigPlugin"
        }
        register("pokedex.kmp.feature.api.config.plugin") {
            id = "pokedex.kmp.feature.api.config.plugin"
            implementationClass = "KmpFeatureApiConfigPlugin"
        }
    }
}
