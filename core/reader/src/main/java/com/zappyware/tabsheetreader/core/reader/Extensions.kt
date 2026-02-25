package com.zappyware.tabsheetreader.core.reader

import java.io.InputStream
import java.nio.ByteBuffer

fun InputStream.readI32(): Int {
    val size = Integer.BYTES
    val integerBytes = ByteArray(size)
    read(integerBytes, 0, size)
    return ByteBuffer.allocate(size).also {
        it.put(integerBytes.reversed().toByteArray())
        it.rewind()
    }.getInt()
}

fun InputStream.readByteSizeString(count: Int): String {
    val size = read()
    val bytes = ByteArray(count)
    read(bytes, 0, count)
    return String(bytes, 0, size)
}

fun InputStream.readIByteSizeString(): String {
    val size = readI32() - 1
    return readByteSizeString(size)
}
