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
 * @since 0.1.0
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
 * in the compilation process. The constants are generated as a Kotlin object with
 * static constant values, making them accessible throughout the codebase.
 *
 * @since 0.1.0
 */
public abstract class GenerateBuildConstants : Task() {
    /**
     * The key-value pairs that will be converted into constants.
     * This map contains the names and values of the constants that will be generated
     * in the output file. Each entry will become a constant with the key as the name
     * and the value as the string literal value.
     *
     * @since 0.1.0
     */
    @get:Input
    public abstract val properties: MapProperty<String, String>

    /**
     * The directory where the generated build constants file will be stored.
     * This property specifies the output location for the generated Kotlin file
     * containing the build constants. The file will be named BuildConstants.kt
     * and placed in this directory.
     *
     * The directory will be created if it doesn't exist. Typically, this should be
     * set to a location within the build directory to ensure it's cleaned up during
     * the 'clean' task.
     *
     * @since 0.1.0
     */
    @get:InputDirectory
    public abstract val buildConstantDirectory: DirectoryProperty

    /**
     * The group of the project.
     * 
     * This property defines the package name for the generated BuildConstants class.
     * It's typically set to the base package of your project followed by '.build'
     * to keep the constants in a dedicated subpackage.
     *
     * @since 0.1.0
     */
    @get:Input
    public abstract val projectGroup: Property<String>

    /**
     * Whether the generated file should be internal or public.
     * 
     * This property controls the visibility of the generated BuildConstants class.
     * When set to true (default), the class will be marked as 'internal', making it
     * accessible only within the same module. When set to false, the class will be
     * marked as 'public', making it accessible from other modules.
     *
     * @since 0.1.0
     */
     @get:Input
     public abstract val onlyInternal: Property<Boolean>

    /**
     * Executes the task to generate the build constants.
     * 
     * This method is the main entry point for the task execution. It performs the following steps:
     * 1. Extracts the content from the properties map
     * 2. Creates or gets the build constants file
     * 3. Writes the generated content to the file
     *
     * The task is idempotent, meaning it can be run multiple times without side effects.
     * Each execution will overwrite the previous generated file.
     *
     * @since 0.1.0
     */
    @TaskAction
    override fun execute() {
        val content = extractContent()
        val buildConstantsFile = createBuildConstantsFile()

        writeContentToBuildConstantsFile(buildConstantsFile, content)
    }

    /**
     * Extracts the content from the properties.
     *
     * This method transforms the key-value pairs from the properties map into
     * Kotlin constant declarations. Each entry in the map becomes a line in the
     * generated file with the format: `const val KEY = "VALUE"`.
     *
     * The method handles all property values as strings, so numeric values or other
     * types will be converted to string literals in the generated code.
     *
     * @return The constants formatted as Kotlin code in the form of a [String].
     * @since 0.1.0
     */
    private fun extractContent(): String {
        return properties.get().entries.joinToString("\n") {
            "    const val ${it.key} = \"${it.value}\""
        }
    }

    /**
     * Creates the build constants file.
     *
     * This method creates or gets the file that will contain the generated build constants.
     * The file is created in the directory specified by the [buildConstantDirectory] property
     * and is always named "BuildConstants.kt".
     *
     * If the file already exists, it will be reused (and its contents will be overwritten
     * later in the process). If it doesn't exist, a new file will be created.
     *
     * The method ensures that the parent directories exist before attempting to create the file.
     *
     * @return The File object representing the build constants file, ready for writing.
     * @since 0.1.0
     */
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
     * This method generates the complete Kotlin source file for the build constants
     * and writes it to the specified file. The generated file includes:
     * 
     * 1. A warning comment that the file is generated and should not be edited
     * 2. The package declaration based on [projectGroup]
     * 3. The BuildConstants object declaration with visibility based on [onlyInternal]
     * 4. The constant declarations from the [content] parameter
     *
     * The method completely overwrites any existing content in the file.
     *
     * @param buildConstantsFile The file to write the content to.
     * @param content The formatted constant declarations to include in the file.
     *
     * @since 0.1.0
     */
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
