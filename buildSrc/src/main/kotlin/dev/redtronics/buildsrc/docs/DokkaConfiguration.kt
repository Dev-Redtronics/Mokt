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

package dev.redtronics.buildsrc.docs

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * Contains the configuration for the Dokka plugin.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
public abstract class DokkaConfiguration @Inject constructor(objects: ObjectFactory) {
    /**
     * The name of the project.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public val name: Property<String> = objects.property(String::class.java).apply { set("Mokt") }

    /**
     * The description of the project.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    public val description: Property<String> = objects.property(String::class.java).apply { set("A Mokt project") }
}
