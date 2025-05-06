package dev.redtronics.mokt

import dev.redtronics.mokt.network.client
import dev.redtronics.mokt.network.defaultJson
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

public abstract class OAuth2 {
    public abstract val name: String

    public abstract val clientId: String

    public var clientSecret: String? = null

    public var httpClient: HttpClient = client

    public var json: Json = defaultJson
}

public fun microsoftOAuth2(clientId: String, builder: MicrosoftBuilder.() -> Unit): Microsoft {
    val microsoft = MicrosoftBuilder(clientId)
    microsoft.builder()
    return microsoft.build()
}

public fun keycloakOAuth2(clientId: String, builder: KeycloakBuilder.() -> Unit): Keycloak {
    val keycloak = KeycloakBuilder(clientId)
    keycloak.builder()
    return keycloak.build()
}
