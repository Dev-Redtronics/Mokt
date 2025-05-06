package dev.redtronics.mokt

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
public actual fun decodeBase64(str: String): ByteArray {
    return Base64.decode(str.toByteArray())
}