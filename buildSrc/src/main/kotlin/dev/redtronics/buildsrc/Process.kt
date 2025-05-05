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
 * Represents a process that can be executed.
 *
 * @since 0.0.1
 * */
internal interface Process {
    /**
     * Executes the process.
     *
     * @since 0.0.1
     * */
    fun execute()
}

/**
 * Represents a task that can be executed.
 *
 * @since 0.0.1
 * */
public abstract class Task : DefaultTask(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}

/**
 * Represents an executable like command line that can be executed.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public abstract class Executable : Exec(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}
