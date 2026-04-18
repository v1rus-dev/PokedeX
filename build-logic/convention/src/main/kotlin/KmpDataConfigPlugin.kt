import extensions.commonMainDependencies
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpDataConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.base.config.plugin")
            }

            commonMainDependencies {
                implementation(project(":core:database"))
                implementation(project(":core:network:api"))
                implementation(libs.koin.core)
            }
        }
    }
}
