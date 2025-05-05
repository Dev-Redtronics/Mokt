package dev.redtronics.buildsrc.cinterop

import dev.redtronics.buildsrc.Project
import org.gradle.api.Project as GradleProject
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.nio.file.Path

/**
 * Resolves the path to the cinterop definition file.
 *
 * @param project The Gradle project instance
 * @return The resolved [Path] to the cinterop.def file
 *
 * @since 0.1.0
 */
private fun resolveCinteropDefPath(project: GradleProject): Path = project.rootProject.file("cinterop/cinterop.def").toPath()

/**
 * Applies the cinterop configuration to all [KotlinNativeTargetWithHostTests] targets.
 * Configures the compilation and package settings for native interop.
 *
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
 * Applies the cinterop configuration to all [KotlinNativeTarget] targets.
 * Configures the compilation and package settings for native interop.
 *
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
