import com.android.build.api.dsl.LibraryExtension
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.androidLibrary.get().pluginId)
                apply("android.base.config")
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> {
                    namespace = buildNamespace(
                        base = "yegor.cheprasov.pockedex",
                        projectPath = project.path,
                    )
                }
            }
        }
    }

    private fun buildNamespace(
        base: String,
        projectPath: String,
    ): String {
        return projectPath
            .removePrefix(":")
            .split(":")
            .filter { it.isNotBlank() }
            .joinToString(separator = ".", prefix = "$base.") { segment ->
                segment
                    .replace("-", ".")
                    .replace("_", ".")
                    .split(".")
                    .filter { it.isNotBlank() }
                    .joinToString(".") { it.sanitizeNamespacePart() }
            }
            .replace("..", ".")
    }

    private fun String.sanitizeNamespacePart(): String {
        val cleaned = lowercase()
            .replace(Regex("[^a-z0-9]"), "")

        return when {
            cleaned.isEmpty() -> "module"
            cleaned.first().isDigit() -> "_$cleaned"
            else -> cleaned
        }
    }
}