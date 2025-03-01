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
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import dev.redtronics.buildsrc.utils.os
import javax.inject.Inject
import java.io.File

public abstract class GenerateCompilerDefinitionFiles @Inject constructor() : Task() {
    @get:InputDirectory
    public abstract val nativeMoktDirectory: DirectoryProperty

    @get:InputDirectory
    public abstract val cinteropDirectory: DirectoryProperty

    @TaskAction
    override fun execute() {
        val nativeMoktDir = nativeMoktDirectory.get().asFile
        val includeDirectory = nativeMoktDir.resolve("include")
        val hFileName = validateHFiles(includeDirectory)

        val defFile = validateDefinitionFile()
        writeDefinitionContent(defFile, includeDirectory, hFileName, nativeMoktDir)
    }

    private fun validateHFiles(includeDirectory: File): String {
        val allHFiles = includeDirectory.listFiles { _, name -> name.endsWith(".h") }
        if (allHFiles.size > 1) {
            throw IllegalStateException("There must be exactly one header file in the include directory")
        }
        return allHFiles.first().name
    }

    private fun validateDefinitionFile(): File {
        val defFile = cinteropDirectory.get().asFile.resolve("cinterop.def")
        if (!defFile.exists()) {
            defFile.createNewFile()
        }
        return defFile
    }

    private fun writeDefinitionContent(defFile: File, includeDirectory: File, hFileName: String, nativeMoktDir: File) {
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
