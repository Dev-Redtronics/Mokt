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

import dev.redtronics.buildsrc.Executable
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
 * Task that compiles the Rust code in release mode.
 * This task executes the Rust compiler (cargo) to build the native implementation
 * that will be used by the Kotlin code through C interoperability.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
public abstract class CompileRust @Inject constructor() : Executable() {
    /**
     * The directory containing the Rust project to be compiled.
     * This property specifies the location of the Rust source code and Cargo.toml file
     * that will be used as the working directory for the compilation process.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     */
    @get:InputDirectory
    public abstract val nativeMoktDirectory: DirectoryProperty

    /**
     * Executes the Rust compilation process in release mode.
     * This method sets the working directory to the Rust project location and
     * runs the 'cargo build --release' command to compile the Rust code with optimizations.
     * The resulting binary will be placed in the target/release directory.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     */
    @TaskAction
    override fun execute() {
        workingDir(nativeMoktDirectory)
        commandLine("cargo", "build", "--release")
    }
}
