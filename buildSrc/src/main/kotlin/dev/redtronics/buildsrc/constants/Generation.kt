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

/**
 * Resolves the build constant directory.
 *
 * @param project The project to resolve the build constant directory for.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
internal fun BuildConstantsConfiguration.buildConstantDir(project: Project) = project.layout.buildDirectory
    .dir("generated/templates")
    .get()
    .asFile

/**
 * Generates the build constants.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
abstract class GenerateBuildConstants : Task() {
    /**
     * The properties to generate the build constants from.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Input
    abstract val properties: MapProperty<String, String>

    /**
     * The directory to store the build constants in.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:InputDirectory
    abstract val buildConstantDirectory: DirectoryProperty

    /**
     * The group of the project.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Input
    abstract val projectGroup: Property<String>

    /**
     * Whether the generated file should be internal or public.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
     @get:Input
     abstract val onlyInternal: Property<Boolean>

    /**
     * Executes the task to generate the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @TaskAction
    override fun execute() {
        val content = extractContent()
        val buildConstantsFile = createBuildConstantsFile()

        writeContentToBuildConstantsFile(buildConstantsFile, content)
    }

    /**
     * Extracts the content from the properties.
     *
     * @return The constants in form of [String].
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    private fun extractContent(): String {
        return properties.get().entries.joinToString("\n") {
            "    const val ${it.key} = \"${it.value}\""
        }
    }

    /**
     * Creates the build constants file.
     *
     * @return The created build constants file.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    private fun createBuildConstantsFile(): File {
        val buildConstantDir = buildConstantDirectory.get().asFile

        val buildConstantsFile = buildConstantDir.resolve("BuildConstants.kt")
        if (!buildConstantsFile.exists()) {
            buildConstantsFile.createNewFile()
        }
        return buildConstantsFile
    }

    /**
     * Writes the content to the build constants file.
     *
     * @param buildConstantsFile The file to write the content to.
     * @param content The content to write to the file.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    private fun writeContentToBuildConstantsFile(buildConstantsFile: File, content: String) {
        val isInternal = onlyInternal.get()

        buildConstantsFile.writeText(
            """
                // This file is generated automatically. Do not edit or modify!
                package ${projectGroup.get()}.build

                ${if (isInternal) "internal" else "public"} object BuildConstants {
                $content
                }
            """.trimIndent()
        )
    }
}
