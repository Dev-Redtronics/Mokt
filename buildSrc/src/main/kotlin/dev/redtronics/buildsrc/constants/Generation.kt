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
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

internal fun BuildConstantsConfiguration.buildConstantDir(project: Project) = project.layout.buildDirectory
    .dir("generated/templates")
    .get()
    .asFile

abstract class GenerateBuildConstants : Task() {
    @get:Input
    abstract val properties: MapProperty<String, String>

    @get:InputDirectory
    abstract val buildConstantDirectory: DirectoryProperty

    @get:Input
    abstract val projectGroup: Property<String>

    @TaskAction
    override fun execute() {
        val content = extractContent()
        val buildConstantsFile = createBuildConstantsFile()

        writeContentToBuildConstantsFile(buildConstantsFile, content)
    }

    private fun extractContent(): String {
        return properties.get().entries.joinToString("\n") {
            "    const val ${it.key} = \"${it.value}\""
        }
    }

    private fun createBuildConstantsFile(): File {
        val buildConstantDir = buildConstantDirectory.get().asFile
        buildConstantDir.mkdirs()

        val buildConstantsFile = buildConstantDir.resolve("BuildConstants.kt")
        if (!buildConstantsFile.exists()) {
            buildConstantsFile.createNewFile()
        }
        return buildConstantsFile
    }

    private fun writeContentToBuildConstantsFile(buildConstantsFile: File, content: String) {
        val group = projectGroup.get()

        buildConstantsFile.writeText(
            """
                // This file is generated automatically. Do not edit or modify!
                package ${group}.build

                internal object BuildConstants {
                $content
                }
            """.trimIndent()
        )
    }
}