import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpComposeConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.compose.base.config.plugin")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(libs.compose.material3)
                    implementation(libs.coil.compose)
                    implementation(libs.coil.compose.core)
                    implementation(libs.coil.network.ktor3)
                    implementation(libs.androidx.lifecycle.runtimeCompose)
                }
            }
        }
    }
}
