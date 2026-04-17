import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpBaseConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.android.library.plugin")
                apply("kmp.base.config")
            }
        }
    }
}
