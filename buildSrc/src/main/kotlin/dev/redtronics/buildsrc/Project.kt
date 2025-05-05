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
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

/**
 * Project settings for Mokt.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public object Project {
    // Project information
    public const val NAME: String = "Mokt"
    public const val GROUP: String = "dev.redtronics"
    public const val DESCRIPTION: String = "A Kotlin Multiplatform SDK for integrating with the Mojang and Minecraft APIs."
    public const val INCEPTION_YEAR: Int = 2024
    public const val URL: String = "https://github.com/Dev-Redtronics/Mokt"

    // CI Management
    public const val CI_NAME: String = "Github Actions"
    public const val CI_URL: String = "https://github.com/Dev-Redtronics/Mokt/actions"

    // License
    public const val LICENSE_NAME: String = "MIT"
    public const val LICENSE_URL: String = "https://opensource.org/licenses/MIT"
    public const val LICENSE_DISTRIBUTION: String = "repo"

    // Issue Management
    public const val ISSUE_TRACKER: String = "GitHub Issues"
    public const val ISSUE_TRACKER_URL: String = "https://github.com/Dev-Redtronics/Mokt/issues"

    // SCM
    public const val SCM_NAME: String = "GitHub"
    public const val SCM_URL: String = "https://github.com/Dev-Redtronics/Mokt.git"
}

/**
 * The Mokt extension.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public interface MoktExtension {
    /**
     * The configuration for the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    @get:Nested
    public val buildConstants: BuildConstantsConfiguration

    /**
     * Configures the build constants.
     *
     * @param action The configuration for the build constants.
     *
     * @since 0.0.1
     * */
    public fun buildConstants(action: Action<BuildConstantsConfiguration>) {
        action.execute(buildConstants)
    }
}
