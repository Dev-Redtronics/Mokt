/*
 * MIT License
 * Copyright 2024 Nils Jäkel
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package dev.redtronics.mokt.builder.device

import dev.redtronics.mokt.Microsoft
import dev.redtronics.mokt.network.interval
import dev.redtronics.mokt.response.AccessResponse
import dev.redtronics.mokt.response.device.CodeErrorResponse
import dev.redtronics.mokt.response.device.DeviceAuthStateError
import dev.redtronics.mokt.response.device.DeviceAuthStateErrorItem
import dev.redtronics.mokt.response.device.DeviceCodeResponse
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.time.Duration.Companion.seconds

/**
 * Builder to configure the Microsoft device authentication flow.
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
public class MicrosoftDeviceBuilder internal constructor(override val provider: Microsoft) : DeviceAuth<Microsoft>() {
    /**
     * The URL to the Microsoft Device Code endpoint.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     */
    override val deviceCodeEndpointUrl: Url
        get() = Url("https://login.microsoftonline.com/${provider.tenant.value}/oauth2/v2.0/devicecode")

    /**
     * The grant type of the Microsoft Device Code endpoint.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    override val grantType: String
        get() = "urn:ietf:params:oauth:grant-type:device_code"

    /**
     * Requests an authorization code from the Microsoft Device Code endpoint.
     *
     * @param onRequestError The function to be called if an error occurs during the authorization code request.
     * @return The [DeviceCodeResponse] of the authorization code request or null if an error occurs.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    override suspend fun requestAuthorizationCode(
        onRequestError: suspend (err: CodeErrorResponse) -> Unit,

    ): DeviceCodeResponse? {
        val response = provider.httpClient.submitForm(
            url = deviceCodeEndpointUrl.toString(),
            formParameters = parameters {
                append("client_id", provider.clientId!!)
                append("scope", provider.scopes.joinToString(" ") { it.value })
            }
        )
        if (!response.status.isSuccess()) {
            onRequestError(provider.json.decodeFromString(CodeErrorResponse.serializer(), response.bodyAsText()))
            return null
        }
        return provider.json.decodeFromString(DeviceCodeResponse.serializer(), response.bodyAsText())
    }

    /**
     * Requests an access token from the Microsoft Device Login endpoint.
     *
     * @param deviceCodeResponse The [DeviceCodeResponse] of the authorization code request.
     * @param onRequestError The function to be called if an error occurs during the access token request.
     * @return The [AccessResponse] of the access token request or null if an error occurs.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    override suspend fun requestAccessToken(
        deviceCodeResponse: DeviceCodeResponse,
        onRequestError: suspend (err: DeviceAuthStateError) -> Unit
    ): AccessResponse? {
        val startTime = getTimeMillis()
        return authLoop(startTime, deviceCodeResponse, onRequestError)
    }

    /**
     * The loop to request an access token from the Microsoft Device Login endpoint.
     *
     * @param startTime The start time of the loop.
     * @param deviceCodeResponse The [DeviceCodeResponse] of the authorization code request.
     * @param onRequestError The function to be called if an error occurs during the access token request.
     * @return The [AccessResponse] of the access token request or null if an error occurs.
     *
     * @since 0.0.1
     * @author Nils Jäkel
     * */
    override suspend fun authLoop(
        startTime: Long,
        deviceCodeResponse: DeviceCodeResponse,
        onRequestError: suspend (err: DeviceAuthStateError) -> Unit
    ): AccessResponse? = interval(
        interval = deviceCodeResponse.interval.seconds,
        cond = { getTimeMillis() - startTime < deviceCodeResponse.expiresIn * 1000 }
    ) {
        val response = provider.httpClient.submitForm(
            url = provider.tokenEndpointUrl.toString(),
            formParameters = parameters {
                append("client_id", provider.clientId!!)
                append("device_code", deviceCodeResponse.deviceCode)
                append("grant_type", grantType)
            }
        )

        val responseBody = response.bodyAsText()
        if (responseBody.contains("error")) {
            val errorResponse = provider.json.decodeFromString(DeviceAuthStateError.serializer(), responseBody)
            if (errorResponse.error != DeviceAuthStateErrorItem.AUTHORIZATION_PENDING) {
                onRequestError(errorResponse)
                cancel()
            }
            return@interval null
        }

        codeServer?.stop()
        return@interval provider.json.decodeFromString(AccessResponse.serializer(), responseBody)
    }
}
