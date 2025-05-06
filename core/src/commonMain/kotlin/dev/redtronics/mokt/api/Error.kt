package dev.redtronics.mokt.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MojangApiError(
    val path: String,
    @SerialName("errorMessage")
    val msg: String
): Exception()