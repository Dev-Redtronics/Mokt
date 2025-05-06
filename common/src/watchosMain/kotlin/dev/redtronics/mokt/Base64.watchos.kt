package dev.redtronics.mokt

import dev.redtronics.mokt.cinterop.decode_base64
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.getBytes

@OptIn(ExperimentalForeignApi::class)
public actual fun decodeBase64(str: String): ByteArray {
    return decode_base64(str).getBytes()
}