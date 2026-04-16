import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("org.jetbrains.kotlin.multiplatform")
//    id("org.jetbrains.kotlin.plugin.serialization")
}

configure<KotlinMultiplatformExtension> {
    androidTarget()

    iosArm64()
    iosSimulatorArm64()
}