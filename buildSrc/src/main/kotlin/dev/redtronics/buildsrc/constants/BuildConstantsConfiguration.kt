package dev.redtronics.buildsrc.constants

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import javax.inject.Inject

/**
 * Contains the configuration for the build constants.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
abstract class BuildConstantsConfiguration @Inject constructor(objects: ObjectFactory) {
    /**
     * Contains the properties to add to the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    val properties: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).apply { set(emptyMap()) }

    /**
     * Makes the build constants as internal source.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    val onlyInternal = objects.property(Boolean::class.java).apply { set(true) }
}
