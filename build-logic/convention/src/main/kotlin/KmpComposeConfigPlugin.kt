import extensions.commonMainDependencies
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpComposeConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.base.config.plugin")
                apply("kmp.compose.config")
            }

            commonMainDependencies {
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
        }
    }
}
