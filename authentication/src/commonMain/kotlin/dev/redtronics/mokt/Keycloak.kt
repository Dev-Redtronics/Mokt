package dev.redtronics.mokt

@MoktDSL
public class KeycloakBuilder internal constructor(override val clientId: String) : OAuth2() {
    override val name: String
        get() = "keycloak"

    internal fun build() = Keycloak(this)
}

public class Keycloak internal constructor(private val builder: KeycloakBuilder) {
    public val name: String
        get() = builder.name

    public val clientId: String
        get() = builder.clientId

    public val clientSecret: String?
        get() = builder.clientSecret
}