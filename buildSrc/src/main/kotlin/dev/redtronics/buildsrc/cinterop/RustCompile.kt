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

package dev.redtronics.buildsrc.cinterop

import dev.redtronics.buildsrc.Executable
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 * Compiles the rust release.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
abstract class CompileRust @Inject constructor() : Executable() {
    @get:InputDirectory
    abstract val nativeMoktDirectory: DirectoryProperty

    @TaskAction
    override fun execute() {
        workingDir(nativeMoktDirectory)
        commandLine("cargo", "build", "--release")
    }
}
