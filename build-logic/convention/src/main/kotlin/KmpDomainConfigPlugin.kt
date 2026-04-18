import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpDomainConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.kmp.base.config.plugin")
            }
        }
    }
}
