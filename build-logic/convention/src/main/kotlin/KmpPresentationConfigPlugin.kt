import extensions.commonMainDependencies
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpPresentationConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.compose.config.plugin")
            }

            commonMainDependencies {
                implementation(libs.jetbrains.navigation3.ui)
                implementation(libs.jetbrains.material3.adaptiveNavigation3)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }
    }
}
