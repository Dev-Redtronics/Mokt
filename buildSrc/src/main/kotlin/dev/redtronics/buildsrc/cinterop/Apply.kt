package dev.redtronics.buildsrc.cinterop

import dev.redtronics.buildsrc.Project
import org.gradle.api.Project as GradleProject
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.nio.file.Path

/**
 * Resolves the path to the cinterop definition file in the project structure.
 * This function locates the standard cinterop.def file that contains the C interoperability
 * definitions required for native compilation.
 *
 * @param project The Gradle project instance to resolve paths against
 * @return The resolved [Path] to the cinterop.def file in the project's cinterop directory
 *
 * @since 0.1.0
 */
private fun resolveCinteropDefPath(project: GradleProject): Path = project.rootProject.file("cinterop/cinterop.def").toPath()

/**
 * Applies the C interoperability configuration to [KotlinNativeTargetWithHostTests] targets.
 * This extension function configures the compilation and package settings for native interop,
 * setting up the necessary cinterop definitions and package structure for the target.
 * It's specifically designed for native targets that support host-based testing.
 *
 * @receiver The Kotlin native target with host tests to configure
 * @since 0.1.0
 */
public fun KotlinNativeTargetWithHostTests.applyNativeConfiguration() {
    val path = resolveCinteropDefPath(project)

    compilations.getByName("main") {
        cinterops {
            create(Project.NAME.lowercase()) {
                defFile(path)
                packageName("${Project.GROUP}.${Project.NAME.lowercase()}.cinterop")
            }
        }
    }
}

/**
 * Applies the C interoperability configuration to standard [KotlinNativeTarget] targets.
 * This extension function configures the compilation and package settings for native interop,
 * setting up the necessary cinterop definitions and package structure for the target.
 * This version is used for general native targets without host testing capabilities.
 *
 * @receiver The standard Kotlin native target to configure
 * @since 0.1.0
 */
public fun KotlinNativeTarget.applyNativeConfiguration() {
    val path = resolveCinteropDefPath(project)

    compilations.getByName("main") {
        cinterops {
            create(Project.NAME.lowercase()) {
                defFile(path)
                packageName("${Project.GROUP}.${Project.NAME.lowercase()}.cinterop")
            }
        }
    }
}
