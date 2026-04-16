import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpFeatureBaseConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.base.config.plugin")
            }
        }
    }
}
