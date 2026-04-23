import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpUiConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.compose.base.config.plugin")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(project(":core:common"))
                    implementation(project(":core:design"))
                    implementation(project(":core:resources"))
                    implementation(libs.koin.core)
                    implementation(libs.kotlinx.coroutines.core)
                    implementation(libs.kotlinx.serialization.core)
                }
            }
        }
    }
}
