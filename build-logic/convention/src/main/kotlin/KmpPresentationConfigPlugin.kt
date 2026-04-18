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
                implementation(project(":core:design"))
                implementation(libs.androidx.navigation3.runtime)
                implementation(libs.jetbrains.navigation3.ui)
                implementation(libs.jetbrains.material3.adaptiveNavigation3)
                implementation(libs.androidx.lifecycle.viewmodel.navigation3)
                implementation(libs.koin.navigation3)
                implementation(project(":core:resources"))
            }
        }
    }
}
