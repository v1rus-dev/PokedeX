import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpPresentationConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.compose.config.plugin")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(project(":core:design"))
                    implementation(project(":core:resources"))
                    implementation(libs.kotlinx.coroutines.core)
                    implementation(libs.kotlinx.serialization.core)
                    implementation(libs.androidx.navigation3.runtime)
                    implementation(libs.jetbrains.navigation3.ui)
                    implementation(libs.jetbrains.material3.adaptiveNavigation3)
                    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
                    implementation(libs.koin.navigation3)
                }
            }
        }
    }
}
