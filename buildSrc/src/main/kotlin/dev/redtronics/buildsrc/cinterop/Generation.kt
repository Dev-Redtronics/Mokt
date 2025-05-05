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

import dev.redtronics.buildsrc.utils.OsType
import dev.redtronics.buildsrc.Project
import dev.redtronics.buildsrc.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import dev.redtronics.buildsrc.utils.os
import org.gradle.api.tasks.InputFile
import javax.inject.Inject
import java.io.File

/**
 * Task that generates the compiler definition files for C interoperability.
 * This task creates the necessary definition files that allow Kotlin code to interact with native C libraries.
 *
 * @since 0.1.0
 */
public abstract class GenerateCompilerDefinitionFiles @Inject constructor() : Task() {
    /**
     * The directory containing the native mokt implementation files.
     * This directory is used as the source for header files and compiled libraries.
     *
     * @since 0.1.0
     */
    @get:InputDirectory
    public abstract val nativeMoktDirectory: DirectoryProperty

    /**
     * The directory where generated C interoperability files will be stored.
     * This directory will contain the output files produced by this task.
     *
     * @since 0.1.0
     */
    @get:InputDirectory
    public abstract val cinteropDirectory: DirectoryProperty

    /**
     * The file where the C interoperability definition will be written.
     * This file contains configuration for the Kotlin/Native compiler to properly
     * interface with the C libraries.
     *
     * @since 0.1.0
     */
    @get:InputFile
    public abstract val cinteropDefFile: RegularFileProperty

    /**
     * Executes the task to generate compiler definition files.
     * This method validates the header files in the include directory and
     * writes the appropriate definition content to the output file.
     *
     * @since 0.1.0
     */
    @TaskAction
    override fun execute() {
        val nativeMoktDir = nativeMoktDirectory.get().asFile
        val includeDirectory = nativeMoktDir.resolve("include")
        val hFileName = validateHFiles(includeDirectory)
        writeDefinitionContent(includeDirectory, hFileName, nativeMoktDir)
    }

    /**
     * Validates that exactly one header file exists in the include directory.
     * This method ensures that there is one and only one .h file in the specified directory,
     * throwing an exception if this condition is not met.
     *
     * @param includeDirectory The directory containing header files to validate
     * @return The name of the single header file found
     * @throws IllegalStateException If more than one header file is found
     *
     * @since 0.1.0
     */
    private fun validateHFiles(includeDirectory: File): String {
        val allHFiles = includeDirectory.listFiles { _, name -> name.endsWith(".h") }
        if (allHFiles.size > 1) {
            throw IllegalStateException("There must be exactly one header file in the include directory")
        }
        return allHFiles.first().name
    }

    /**
     * Writes the C interoperability definition content to the specified file.
     * This method generates platform-specific definition content based on the current operating system,
     * including appropriate paths to headers and libraries.
     *
     * @param includeDirectory The directory containing the header files to reference
     * @param hFileName The name of the header file to include in the definition
     * @param nativeMoktDir The root directory containing the native mokt implementation
     *
     * @since 0.1.0
     */
    private fun writeDefinitionContent(includeDirectory: File, hFileName: String, nativeMoktDir: File) {
        val defFile = cinteropDefFile.get().asFile
        when (os) {
            OsType.WINDOWS -> {
                defFile.writeText(
                    """
                        headers = $hFileName
                        staticLibraries = ${Project.NAME.lowercase()}_native.lib
                        compilerOpts = -I${includeDirectory.absolutePath.replace("\\", "/")}
                        libraryPaths = ${nativeMoktDir.resolve("target/release").absolutePath.replace("\\", "/")}
                    """.trimIndent()
                )
            }

            else -> {
                defFile.writeText(
                    """
                        headers = $hFileName
                        staticLibraries = lib${Project.NAME.lowercase()}_native.a
                        compilerOpts = -I$includeDirectory
                        libraryPaths = ${nativeMoktDir.resolve("target/release")}
                    """.trimIndent()
                )
            }
        }
    }
}
