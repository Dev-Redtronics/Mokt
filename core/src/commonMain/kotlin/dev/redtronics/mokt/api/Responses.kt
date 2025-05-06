package dev.redtronics.mokt.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Profile(@SerialName("id") val uuid: String, @SerialName("name") val username: String)