package extensions

import com.sun.tools.attach.spi.AttachProvider.providers
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

val Project.projectJavaVersion: JavaVersion
    get() = JavaVersion.toVersion(libs.versions.javaConvention.get().toInt())

fun Project.hasIosTargets(): Boolean {
    return providers.gradleProperty("pokedex.enableIosTargets")
        .map(String::toBoolean)
        .orElse(
            providers.systemProperty("os.name")
                .map { it.startsWith("Mac", ignoreCase = true) }
        ).get()
}