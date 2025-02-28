/*
 * MIT License
 * Copyright 2024 Nils Jäkel  & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.buildsrc.constants

import dev.redtronics.buildsrc.Task
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

internal fun BuildConstantsConfiguration.buildConstantDir(project: Project) = project.layout.buildDirectory
    .dir("generated/templates")
    .get()
    .asFile

abstract class GenerateBuildConstants : Task() {
    @TaskAction
    override fun execute() {

    }
}

internal fun generateBuildConstants(project: Project, buildConstantsConfiguration: BuildConstantsConfiguration) {
    val content = buildConstantsConfiguration.properties.get().entries.joinToString("\n") {
        "    const val ${it.key} = \"${it.value}\""
    }
    val generatedDir = buildConstantsConfiguration.buildConstantDir(project)
    generatedDir.mkdirs()

    val buildConstantsFile = generatedDir.resolve("BuildConstants.kt")
    if (!buildConstantsFile.exists()) {
        buildConstantsFile.createNewFile()
    }

    buildConstantsFile.outputStream().use { outputStream ->
        outputStream.write(
            """
// This file is generated automatically. Do not edit or modify!
package ${project.group}.build

internal object BuildConstants {
$content
}
            """.trimIndent().toByteArray()
        )
    }
}
