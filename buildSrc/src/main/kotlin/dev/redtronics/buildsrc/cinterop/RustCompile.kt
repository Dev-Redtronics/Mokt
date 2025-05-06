package dev.redtronics.buildsrc.cinterop

import dev.redtronics.buildsrc.Executable
import dev.redtronics.buildsrc.utils.OsType
import dev.redtronics.buildsrc.utils.os
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

/**
 * Task that compiles the Rust code in release mode.
 * This task executes the Rust compiler (cargo) to build the native implementation
 * that will be used by the Kotlin code through C interoperability.
 *
 * @since 0.1.0
 */
public abstract class CompileRust @Inject constructor() : Executable() {
    /**
     * The directory containing the Rust project to be compiled.
     * This property specifies the location of the Rust source code and Cargo.toml file
     * that will be used as the working directory for the compilation process.
     *
     * @since 0.1.0
     */
    @get:InputDirectory
    public abstract val nativeMoktDirectory: DirectoryProperty

    /**
     * Executes the Rust compilation process in release mode.
     * This method sets the working directory to the Rust project location and
     * runs the 'cargo build --release' command to compile the Rust code with optimizations.
     * The resulting binary will be placed in the target/release directory.
     *
     * @since 0.1.0
     */
    @TaskAction
    override fun execute() {
        workingDir(nativeMoktDirectory)

        when(os) {
            OsType.MACOS -> {
                val pathVars = System.getenv("PATH")
                val paths = pathVars.split(":")
                val cargoPath = paths.find { it.contains(".cargo${File.separatorChar}bin") } ?: ""

                commandLine("${cargoPath}${File.separatorChar}cargo.exe", "build", "--release")
            }
            else -> {
                commandLine("cargo", "build", "--release")
            }
        }
    }
}
