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
 * Resolves the directory where build constants will be generated.
 * This extension function determines the standard location for generated build constants
 * within the project's build directory structure.
 *
 * @param project The Gradle project to resolve the build directory against
 * @return The File object representing the directory for build constants
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
internal fun BuildConstantsConfiguration.buildConstantDir(project: Project) = project.layout.buildDirectory
    .dir("generated/templates")
    .get()
    .asFile

/**
 * Task that generates build-time constants for the project.
 * This task creates a Kotlin source file containing constant values that are determined
 * during the build process. These constants can be used throughout the application to
 * access build-specific information like version numbers, build timestamps, etc.
 *
 * The generated file will be placed in the build directory and will be included
 * in the compilation process.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
public abstract class GenerateBuildConstants : Task() {
    /**
     * The key-value pairs that will be converted into constants.
     * This map contains the names and values of the constants that will be generated
     * in the output file. Each entry will become a constant with the key as the name
     * and the value as the string literal value.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     */
    @get:Input
    public abstract val properties: MapProperty<String, String>

    /**
     * The directory where the generated build constants file will be stored.
     * This property specifies the output location for the generated Kotlin file
     * containing the build constants. The file will be named BuildConstants.kt
     * and placed in this directory.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     */
    @get:InputDirectory
    public abstract val buildConstantDirectory: DirectoryProperty

    /**
     * The group of the project.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Input
    public abstract val projectGroup: Property<String>

    /**
     * Whether the generated file should be internal or public.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
     @get:Input
     public abstract val onlyInternal: Property<Boolean>

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
