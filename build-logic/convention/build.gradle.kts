import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "yegor.cheprasov.pockedex.buildlogic.convention"

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
        register("pockedex.android.application.plugin") {
            id = "pockedex.android.application.plugin"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("pockedex.android.library.plugin") {
            id = "pockedex.android.library.plugin"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("pockedex.kmp.base.config.plugin") {
            id = "pockedex.kmp.base.config.plugin"
            implementationClass = "KmpBaseConfigPlugin"
        }
    }
}
