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

package dev.redtronics.buildsrc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Exec

/**
 * Represents a process that can be executed within the build system.
 * This interface defines the contract for all executable processes in the project,
 * providing a common abstraction for different types of tasks and commands.
 *
 * @since 0.1.0
 */
internal interface Process {
    /**
     * Executes the process implementation.
     * This method should contain the logic to perform the specific task or command
     * that the implementing class is designed to execute.
     *
     * @since 0.1.0
     */
    fun execute()
}

/**
 * Base class for all custom Gradle tasks in the project.
 * This abstract class extends Gradle's DefaultTask and implements the Process interface,
 * providing a foundation for creating custom build tasks with consistent behavior.
 * All tasks created from this class will be automatically assigned to the project's group.
 *
 * @since 0.1.0
 */
public abstract class Task : DefaultTask(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}

/**
 * Base class for tasks that execute external commands or processes.
 * This abstract class extends Gradle's Exec task and implements the Process interface,
 * providing a foundation for creating tasks that run external commands with consistent behavior.
 * All executable tasks created from this class will be automatically assigned to the project's group.
 *
 * @since 0.1.0
 */
public abstract class Executable : Exec(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}
