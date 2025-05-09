package dev.redtronics.buildsrc.utils

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Configures a task to run before all Kotlin and Java compilation tasks.
 * This extension function sets up task dependencies to ensure that the specified task
 * is executed before any Kotlin or Java compilation occurs in the project. This is useful
 * for tasks that need to generate code or resources that are required during compilation.
 *
 * @param task The task that should be executed before compilation
 * @receiver The Gradle project to configure
 *
 * @since 0.1.0
 */
internal fun Project.executeTaskBeforeCompile(task: Task) {
    tasks.withType<KotlinCompilationTask<*>> {
        dependsOn(task)
    }

    tasks.withType<JavaCompile> {
        dependsOn(task)
    }
}

/**
 * Configures multiple tasks to run before all Kotlin and Java compilation tasks.
 * This extension function is a convenience method that allows configuring multiple tasks
 * at once to be executed before compilation. It internally calls [executeTaskBeforeCompile]
 * for each provided task.
 *
 * @param tasks Variable number of tasks that should be executed before compilation
 * @receiver The Gradle project to configure
 *
 * @since 0.1.0
 * @see executeTaskBeforeCompile
 */
internal fun Project.executeTasksBeforeCompile(vararg tasks: Task) {
    tasks.forEach { task ->
        executeTaskBeforeCompile(task)
    }
}
