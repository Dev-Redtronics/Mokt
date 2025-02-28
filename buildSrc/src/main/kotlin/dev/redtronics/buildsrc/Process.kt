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

internal interface Process {
    fun execute()
}

abstract class Task : DefaultTask(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}

abstract class Executable : Exec(), Process {
    init {
        group = Project.NAME.lowercase()
    }
}
