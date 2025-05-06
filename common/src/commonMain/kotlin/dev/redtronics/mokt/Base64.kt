package dev.redtronics.mokt

/**
 * Decodes a Base64 string to a ByteArray.
 *
 * @param str The Base64 string to decode
 * @return The decoded ByteArray
 */
public expect fun decodeBase64(str: String): ByteArray

/**
 * Decodes a Base64 string to a String using UTF-8 encoding.
 *
 * @param str The Base64 string to decode
 * @return The decoded String
 */
public fun decodeBase64ToString(str: String): String {
    return decodeBase64(str).decodeToString()
}