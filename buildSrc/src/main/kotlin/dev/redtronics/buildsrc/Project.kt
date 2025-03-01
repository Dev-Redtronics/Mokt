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
public object Project {
    public const val NAME: String = "Mokt"
    public const val DESCRIPTION: String = "A Kotlin Multiplatform SDK for interaction with the Minecraft World."
    public const val GROUP: String = "dev.redtronics.mokt"
    public const val URL: String = "https://mokt.redtronics.dev"
    public const val GITLAB_URL: String = "https://code.redtronics.dev"
}

/**
 * The Mokt extension.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public interface MoktExtension {
    /**
     * The configuration for the Dokka plugin.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Nested
    public val docs: DokkaConfiguration

    /**
     * The configuration for the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Nested
    public val buildConstants: BuildConstantsConfiguration

    /**
     * Configures the Dokka plugin.
     *
     * @param action The configuration for the Dokka plugin.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public fun docs(action: Action<DokkaConfiguration>) {
        action.execute(docs)
    }

    /**
     * Configures the build constants.
     *
     * @param action The configuration for the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public fun buildConstants(action: Action<BuildConstantsConfiguration>) {
        action.execute(buildConstants)
    }
}
