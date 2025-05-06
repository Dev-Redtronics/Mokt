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

import dev.redtronics.mokt.api.PublicMojangApi
import dev.redtronics.mokt.network.client
import dev.redtronics.mokt.network.defaultJson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.isSuccess
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.uuid.Uuid



public class Mojang {
    private val httpClient = client
    private val json = defaultJson
    private val authContext = AuthContext()

    public suspend fun authenticate(tocken: String, block: () -> Unit): Nothing = TODO()

    public suspend fun public(block: suspend PublicMojangApi.() -> Unit) {
        block(PublicMojangApi(httpClient))
    }
}

public suspend fun mojang(builder: suspend Mojang.() -> Unit) {
    builder(Mojang())
}