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
 * Generates the compiler definition files.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public abstract class GenerateCompilerDefinitionFiles @Inject constructor() : Task() {
    /**
     * The directory to store the native mokt files in.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:InputDirectory
    public abstract val nativeMoktDirectory: DirectoryProperty

    /**
     * The directory to store the cinterop files in.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:InputDirectory
    public abstract val cinteropDirectory: DirectoryProperty

    /**
     * The file to store the cinterop definition in.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:InputFile
    public abstract val cinteropDefFile: RegularFileProperty

    /**
     * Executes the generation of the compiler definition files.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @TaskAction
    override fun execute() {
        val nativeMoktDir = nativeMoktDirectory.get().asFile
        val includeDirectory = nativeMoktDir.resolve("include")
        val hFileName = validateHFiles(includeDirectory)
        writeDefinitionContent(includeDirectory, hFileName, nativeMoktDir)
    }

    /**
     * Validates the header files.
     *
     * @param includeDirectory The directory to validate the header files in.
     * @return The name of the header file.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    private fun validateHFiles(includeDirectory: File): String {
        val allHFiles = includeDirectory.listFiles { _, name -> name.endsWith(".h") }
        if (allHFiles.size > 1) {
            throw IllegalStateException("There must be exactly one header file in the include directory")
        }
        return allHFiles.first().name
    }

    /**
     * Writes the content to the definition file.
     *
     * @param includeDirectory The directory to include the header files from.
     * @param hFileName The name of the header file.
     * @param nativeMoktDir The directory to the native mokt files.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
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
