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

package dev.redtronics.mokt

import dev.redtronics.mokt.network.client
import dev.redtronics.mokt.network.defaultJson
import io.ktor.client.*
import kotlinx.serialization.json.Json

/**
 * Keycloak authentication provider.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public class Keycloak : Provider {
    override val name: String
        get() = "Keycloak"

    override var httpClient: HttpClient = client

    override var json: Json = defaultJson
}