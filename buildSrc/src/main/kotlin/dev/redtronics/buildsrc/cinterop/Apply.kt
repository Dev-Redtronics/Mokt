/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.buildsrc.cinterop

import dev.redtronics.buildsrc.Project
import org.gradle.api.Project as GradleProject
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.nio.file.Path

/**
 * Resolves the path to the cinterop definition file.
 *
 * @param project The project to resolve the path for.
 * @return The [Path] to the cinterop definition file.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
private fun resolveCinteropDefPath(project: GradleProject): Path = project.rootProject.file("cinterop/cinterop.def").toPath()

/**
 * Applies the cinterop configuration to all [KotlinNativeTargetWithHostTests] targets.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
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
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
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
