import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpComposeConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.base.config.plugin")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(libs.compose.runtime)
                    implementation(libs.compose.foundation)
                    implementation(libs.compose.material3)
                    implementation(libs.compose.ui)
                    implementation(libs.compose.uiToolingPreview)
                    implementation(libs.coil.compose)
                    implementation(libs.coil.network.ktor3)
                    implementation(libs.androidx.lifecycle.runtimeCompose)
                }
            }

            dependencies {
                add("androidRuntimeClasspath", libs.compose.uiTooling)
            }
        }
    }
}
