/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.mokt.builder.mojang

import io.ktor.client.*
import kotlinx.serialization.json.Json

/**
 * Base class for all mojang authentication builders.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public abstract class MojangBaseAuthBuilder {
    internal abstract val httpClient: HttpClient
    internal abstract val json: Json
}
