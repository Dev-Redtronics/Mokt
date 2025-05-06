package dev.redtronics.mokt

@MoktDSL
public class MicrosoftBuilder internal constructor(override val clientId: String) : OAuth2() {
    override val name: String
        get() = "microsoft"

    internal fun build() = Microsoft(this)
}

public class Microsoft(private val builder: MicrosoftBuilder) {
    public val name: String
        get() = builder.name

    public val clientId: String
        get() = builder.clientId

    public val clientSecret: String?
        get() = builder.clientSecret
}