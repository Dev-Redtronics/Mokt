package dev.redtronics.buildsrc.docs/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

import org.gradle.api.provider.Property

/**
 * Contains the configuration for the Dokka plugin.
 *
 * @property name The name override for the documentation.
 * @property description The description for the documentation.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
interface DokkaConfiguration {
    val name: Property<String>
    val description: Property<String>
}
