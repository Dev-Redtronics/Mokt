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

import dev.redtronics.buildsrc.constants.BuildConstantsConfiguration
import dev.redtronics.buildsrc.docs.DokkaConfiguration
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

/**
 * Project settings for Mokt.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
object Project {
    const val NAME = "Mokt"
    const val DESCRIPTION = "A Kotlin Multiplatform SDK for interaction with the Minecraft World."
    const val GROUP = "dev.redtronics.mokt"
    const val URL = "https://mokt.redtronics.dev"
    const val GITLAB_URL = "https://code.redtronics.dev"
}

interface MoktExtension {
    @get:Nested
    val docs: DokkaConfiguration

    @get:Nested
    val buildConstants: BuildConstantsConfiguration

    fun docs(action: Action<DokkaConfiguration>) {
        action.execute(docs)
    }

    fun buildConstants(action: Action<BuildConstantsConfiguration>) {
        action.execute(buildConstants)
    }
}
