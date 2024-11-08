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

package dev.redtronics.mokt.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class AccessResponse(
    @SerialName("token_type")
    public val tokenType: String,
    public val scope: String,
    @SerialName("expires_in")
    public val expiresIn: Int,
    @SerialName("ext_expires_in")
    public val extExpiresIn: Int? = null,
    @SerialName("access_token")
    public val accessToken: String,
    @SerialName("refresh_token")
    public val refreshToken: String
)