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
 * @since 0.0.1
 * @author Nils Jäkel
 */
public abstract class BuildConstantsConfiguration @Inject constructor(objects: ObjectFactory) {
    /**
     * Contains the properties to add to the build constants.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public val properties: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java).apply { set(emptyMap()) }

    /**
     * Makes the build constants as internal source.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public val onlyInternal: Property<Boolean> = objects.property(Boolean::class.java).apply { set(true) }
}
