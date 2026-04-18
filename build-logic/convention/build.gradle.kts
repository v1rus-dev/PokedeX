import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "yegor.cheprasov.pokedex.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    implementation(libs.gradleplugin.android)
    implementation(libs.gradleplugin.kotlin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.gradleplugin.base)
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
        register("pokedex.android.application.plugin") {
            id = "pokedex.android.application.plugin"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("pokedex.android.library.plugin") {
            id = "pokedex.android.library.plugin"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("pokedex.kmp.base.config.plugin") {
            id = "pokedex.kmp.base.config.plugin"
            implementationClass = "KmpBaseConfigPlugin"
        }
        register("pokedex.kmp.feature.base.config.plugin") {
            id = "pokedex.kmp.feature.base.config.plugin"
            implementationClass = "KmpFeatureBaseConfigPlugin"
        }
        register("pokedex.kmp.compose.config.plugin") {
            id = "pokedex.kmp.compose.config.plugin"
            implementationClass = "KmpComposeConfigPlugin"
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
