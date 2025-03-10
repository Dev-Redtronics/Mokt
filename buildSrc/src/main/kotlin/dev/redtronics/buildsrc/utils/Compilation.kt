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

package dev.redtronics.buildsrc.utils

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Executes a task before the compilation of Kotlin and Java sources.
 *
 * @param task The task to execute before the compilation.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
internal fun Project.executeTaskBeforeCompile(task: Task) {
    tasks.withType<KotlinCompilationTask<*>> {
        dependsOn(task)
    }

    tasks.withType<JavaCompile> {
        dependsOn(task)
    }
}

/**
 * Executes multiple tasks before the compilation of Kotlin and Java sources.
 *
 * @param tasks The tasks to execute before the compilation.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
internal fun Project.executeTasksBeforeCompile(vararg tasks: Task) {
    tasks.forEach { task ->
        executeTaskBeforeCompile(task)
    }
}
