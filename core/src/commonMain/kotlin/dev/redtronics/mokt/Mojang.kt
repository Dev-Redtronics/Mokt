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
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

public data class AuthContext(
    public var mojangToken: String? = null,
    public var username: String? = null
)

public class MojangApiContext {
    public fun auth(builder: AuthContext.() -> Unit) {

    }


}


public interface MojangApi {
    public val httpClient: HttpClient
}

public open class PublicMojangApi internal constructor(
    override val httpClient: HttpClient = client
): MojangApi {
    public suspend fun isUsernameAvailable(username: String): Boolean {
        val body = httpClient.get("https://api.mojang.com/users/profiles/minecraft/$username").body<String>()
        when (body.lowercase()) {
            "AVAILABLE" -> return true
            "TAKEN" -> return false
            else -> throw Exception("Unknown response: $body")
        }
    }
}

public class PrivateMojangApi internal constructor(
    override val httpClient: HttpClient = client,
): MojangApi, PublicMojangApi() {

}



public class Mojang {
    private val httpClient = client
    private val json = defaultJson
    private val authContext = AuthContext()

    public suspend fun authenticate(tocken: String, block: () -> Unit): Nothing = TODO()

    public suspend fun public(block: () -> Unit) {

    }
}

public fun mojang(builder: Mojang.() -> Unit) {

}