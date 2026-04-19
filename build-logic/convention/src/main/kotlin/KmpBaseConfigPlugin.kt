import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import extensions.libs
import extensions.projectJavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class KmpBaseConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.kotlin.multiplatform.library")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            val enableIosTargets = providers.gradleProperty("pokedex.enableIosTargets")
                .map(String::toBoolean)
                .orElse(
                    providers.systemProperty("os.name")
                        .map { it.startsWith("Mac", ignoreCase = true) }
                )

            extensions.configure<KotlinMultiplatformExtension> {
                if (enableIosTargets.get()) {
                    iosArm64()
                    iosSimulatorArm64()
                }
            }

            pluginManager.withPlugin("com.android.kotlin.multiplatform.library") {
                extensions.configure<KotlinMultiplatformExtension> {
                    targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).all {
                        namespace = buildNamespace(
                            base = "yegor.cheprasov.pokedex",
                            projectPath = path,
                        )
                        compileSdk = libs.versions.android.compileSdk.get().toInt()
                        minSdk = libs.versions.android.minSdk.get().toInt()
                        withHostTest {}
                        compilerOptions {
                            jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
                            freeCompilerArgs.add("-Xjdk-release=${projectJavaVersion}")
                        }
                    }
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
