package dev.redtronics.buildsrc

import dev.redtronics.buildsrc.constants.BuildConstantsConfiguration
import org.gradle.api.Action
import org.gradle.api.tasks.Nested

/**
 * Central configuration object containing project-wide settings and metadata for Mokt.
 * This object defines constants used throughout the build system to maintain
 * consistent project information across all build files and generated artifacts.
 * It includes details about the project, CI, licensing, issue tracking, and source control.
 *
 * @since 0.1.0
 */
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
 * Gradle extension interface for configuring Mokt-specific build settings.
 * This interface provides a type-safe way to configure various aspects of the Mokt build
 * through the Gradle DSL. It exposes configuration options that can be customized
 * in build scripts to control the build process.
 *
 * @since 0.1.0
 */
public interface MoktExtension {
    /**
     * Configuration for build-time constants generation.
     * This property provides access to settings that control how build constants
     * are generated and what values they contain. These constants can be used
     * to inject build-specific information into the compiled code.
     *
     * @since 0.1.0
     */
    @get:Nested
    public val buildConstants: BuildConstantsConfiguration

    /**
     * Configures the build constants using a configuration action.
     * This method provides a DSL-friendly way to configure build constants
     * in Gradle build scripts using lambda expressions or action objects.
     *
     * @param action The configuration action to apply to the build constants
     * @since 0.1.0
     */
    public fun buildConstants(action: Action<BuildConstantsConfiguration>) {
        action.execute(buildConstants)
    }
}
