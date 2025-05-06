package dev.redtronics.mokt

import org.khronos.webgl.Uint8Array

public actual fun decodeBase64(str: String): ByteArray {
    val binary = js("atob")(str)
    val bytes = Uint8Array(length = binary.length)

    for (i in binary.indices) {
        bytes.set(arrayOf(binary.charCodeAt(i).toByte()), i)
    }

    return bytes.unsafeCast<ByteArray>()
}