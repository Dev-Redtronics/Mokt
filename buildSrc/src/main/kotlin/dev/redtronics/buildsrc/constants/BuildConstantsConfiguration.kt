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

package dev.redtronics.buildsrc.constants

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * Contains the configuration for the build constants.
 *
 * This class serves as a configuration container for the build constants generation process.
 * It provides properties to customize how build constants are generated, including what
 * constants to include and their visibility.
 *
 * The configuration is typically used in conjunction with the [GenerateBuildConstants] task
 * to define project-specific build constants.
 *
 * @since 0.1.0
 */
public abstract class BuildConstantsConfiguration @Inject constructor(objects: ObjectFactory) {
    /**
     * Contains the properties to add to the build constants.
     *
     * This property defines the key-value pairs that will be converted into constant
     * declarations in the generated BuildConstants class. Each entry in the map will
     * become a constant with the key as the name and the value as the string literal value.
     *
     * @since 0.1.0
     */
    public val properties: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).apply { set(emptyMap()) }

    /**
     * Controls whether the generated build constants are internal or public.
     *
     * This property determines the visibility modifier of the generated BuildConstants class.
     * When set to true (the default), the class will be marked as 'internal', making it
     * accessible only within the same module. When set to false, the class will be marked
     * as 'public', making it accessible from other modules.
     *
     * @since 0.1.0
     */
    public val onlyInternal: Property<Boolean> = objects.property(Boolean::class.java).apply { set(true) }
}
