package dev.redtronics.mokt.api

import dev.redtronics.mokt.decodeBase64ToString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
public data class Profile(
    @SerialName("id") val uuid: String,
    @SerialName("name") val username: String,
    val legacy: Boolean? = null,
    val properties: List<Property> = emptyList()
)

/**
 * Represents a player property.
 *
 * @property name Name of the property. For now, the only property that exists is textures.
 * @property signature Signature signed with Yggdrasil private key as Base64 string, only exists when unsigned=false.
 * @property value Base64 string with all player textures (skin and cape).
 */
@Serializable
public data class Property(
    val name: String,
    val signature: String? = null,
    val value: String
)

/**
 * Represents the decoded texture value from a property.
 *
 * @property timestamp Unix time in milliseconds the texture is accessed.
 * @property profileId Player's UUID without dashes.
 * @property profileName Player name.
 * @property signatureRequired Only exists when unsigned=false.
 * @property textures Texture information.
 */
@Serializable
public data class TextureData(
    val timestamp: Long,
    @SerialName("profileId") val profileId: String,
    @SerialName("profileName") val profileName: String,
    val signatureRequired: Boolean? = null,
    val textures: Textures = Textures()
)

/**
 * Contains the skin and cape textures.
 *
 * @property SKIN Skin texture. This does not exist if the player does not have a custom skin.
 * @property CAPE Cape texture. If the player does not have a cape, this does not exist.
 */
@Serializable
public data class Textures(
    @SerialName("SKIN") val skin: SkinTexture? = null,
    @SerialName("CAPE") val cape: CapeTexture? = null
)

/**
 * Represents a skin texture.
 *
 * @property url URL to the skin texture.
 * @property metadata Optional. Metadata for the skin.
 */
@Serializable
public data class SkinTexture(
    val url: String,
    val metadata: Metadata? = null
)

/**
 * Represents metadata for a skin texture.
 *
 * @property model "slim" if the skin model is Alex. When skin model is Steve, this metadata does not exist.
 */
@Serializable
public data class Metadata(
    val model: String
)

/**
 * Represents a cape texture.
 *
 * @property url URL to the cape texture.
 */
@Serializable
public data class CapeTexture(
    val url: String
)

/**
 * Decodes the Base64-encoded value of a texture property and returns the TextureData.
 *
 * @param json The JSON serializer to use for deserialization
 * @return The decoded TextureData, or null if decoding fails
 */
public fun Property.decodeTextureData(json: Json): TextureData? {
    if (name != "textures") return null

    return try {
        val decodedValue = decodeBase64ToString(value)
        json.decodeFromString(decodedValue)
    } catch (e: Exception) {
        null
    }
}