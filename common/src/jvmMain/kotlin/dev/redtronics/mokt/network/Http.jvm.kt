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

package dev.redtronics.mokt.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json

public actual val client: HttpClient = HttpClient(CIO) {
    install(Logging) {
        level = LogLevel.INFO
    }

    install(HttpTimeout) {
        connectTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
        requestTimeoutMillis = 1000 * 30
        socketTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
    }

    install(HttpRequestRetry) {
        maxRetries = 3
        retryOnServerErrors(3)
    }

    install(ContentNegotiation) {
        this.json(defaultJson)
    }
}