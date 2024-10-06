/*
 * MIT License
 * Copyright 2024 Nils Jäkel
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

package dev.redtronics.mokt

import dev.redtronics.mokt.builder.XBoxBuilder
import dev.redtronics.mokt.builder.XstsBuilder
import dev.redtronics.mokt.provider.Authentik
import dev.redtronics.mokt.provider.Keycloak
import dev.redtronics.mokt.provider.Microsoft
import dev.redtronics.mokt.provider.response.AccessResponse
import dev.redtronics.mokt.response.XBoxResponse
import dev.redtronics.mokt.response.XstsResponse
import kotlin.reflect.KProperty

/**
 * Provides a [AuthProvider] which can be used to access the selected provider and authenticate with them.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
public class AuthProvider<in T : Provider> @PublishedApi internal constructor(private val provider: T) {
    public val value: @UnsafeVariance T
        get() = provider

    public operator fun getValue(t: T?, property: KProperty<*>): @UnsafeVariance T {
        return provider
    }
}

/**
 * Provides a [AuthProvider] which can be used to access the selected provider and authenticate with them.
 *
 * @param T The provider that should be used.
 * @param builder The builder to configure the provider.
 * @return [AuthProvider] with the selected provider.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 */
public suspend inline fun <reified T : Provider> auth(noinline builder: suspend T.() -> Unit): AuthProvider<T> =
    when (T::class) {
        Microsoft::class -> {
            val microsoft = Microsoft().apply { builder(this as T) }
            if (microsoft.clientId == null) throw IllegalArgumentException("Client id is not set")

            require(Regex("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}").matches(microsoft.clientId!!)) { "Client id is not valid" }
            AuthProvider(microsoft)
        }

        Authentik::class -> AuthProvider(Authentik().apply { builder(this as T) })
        Keycloak::class -> AuthProvider(Keycloak().apply { builder(this as T) })
        else -> {
            throw IllegalArgumentException("Provider ${T::class} is not supported")
        }
    }

public abstract class MojangGameAuth internal constructor() {
    internal abstract val ms: Microsoft
    public abstract fun accessToken(): AccessResponse?

    public suspend fun refreshAccessToken(): AccessResponse? {
        return null
    }

    public suspend fun xBox(
        accessResponse: AccessResponse,
        onRequestError: suspend () -> Unit = {},
        builder: suspend XBoxBuilder.() -> Unit = {}
    ): XBoxResponse? {
        val xBoxBuilder = XBoxBuilder(ms.httpClient, ms.json, accessResponse).apply { builder() }
        return xBoxBuilder.build(onRequestError)
    }

    public suspend fun xsts(
        xBoxResponse: XBoxResponse,
        onRequestError: suspend () -> Unit = {},
        builder: suspend XstsBuilder.() -> Unit = {}
    ): XstsResponse? {
        val xstsBuilder = XstsBuilder(ms.httpClient, ms.json, xBoxResponse).apply { builder() }
        return xstsBuilder.build(onRequestError)
    }

    public suspend fun requestMojangAccessTokenResponse() {

    }

    internal abstract fun build()
}
