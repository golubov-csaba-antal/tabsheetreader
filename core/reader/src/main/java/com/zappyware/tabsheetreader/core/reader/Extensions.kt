package com.zappyware.tabsheetreader.core.reader

import java.io.InputStream
import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Integer.numberOfTrailingZeros
import java.nio.ByteBuffer

fun InputStream.readBoolean(): Boolean {
    val booleanInt = readI8()
    return booleanInt != 0
}

fun InputStream.readI8(): Int {
    val size = Integer.BYTES / 4
    val integerBytes = ByteArray(size)
    read(integerBytes, 0, size)
    // we need the 4-byte sized Int here to get the correct value
    return ByteBuffer.allocate(Integer.BYTES).also {
        it.put(integerBytes)
        it.rewind()
    }.getInt()
}

fun InputStream.readI16(): Int {
    val size = Integer.BYTES / 2
    val integerBytes = ByteArray(size)
    read(integerBytes, 0, size)
    // we need the 4-byte sized Int here to get the correct value
    return ByteBuffer.allocate(Integer.BYTES).also {
        it.put(integerBytes.reversed().toByteArray())
        it.rewind()
    }.getInt()
}

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
    return if(count == 0 || size <= 0 || size == 1 && bytes[0] == 0.toByte()) "" else String(bytes, 0, size)
}

fun InputStream.readIByteSizeString(): String {
    val size = readI32()
    return readByteSizeString(max(size - 1, 0))
}

fun InputStream.readIntSizeString(): String {
    val size = readI32()
    val bytes = ByteArray(size)
    read(bytes, 0, size)
    return if(size == 1 && bytes[0] == 0.toByte()) "" else String(bytes, 0, size)
}

fun String.clean(): String =
    replace("[\n\r]".toRegex(), "")

fun Int.toChannelValue(): Int =
    min(max((this shl 3) - 1, -1), 32767) + 1

fun Int.toRepeatAlternatives(): String? =
    if (this == 0) null
    else buildString {
        var mask = this@toRepeatAlternatives
        while (mask != 0) {
            // Find the index of the rightmost set bit (0-indexed)
            val startBit = numberOfTrailingZeros(mask)
            val start = startBit + 1

            // Find the length of the consecutive run of set bits
            // shifted.inv() will have 0s where 'mask' had 1s. 
            // trailingZeros of that gives the length of the run of 1s.
            val shifted = mask ushr startBit
            val runLength = numberOfTrailingZeros(shifted.inv())
            val end = startBit + runLength

            if (isNotEmpty()) append(", ")
            if (runLength == 1) {
                append(start)
            } else {
                append(start).append('-').append(end)
            }

            // Clear the processed bits.
            mask = if (end >= 32) 0 else (mask ushr end) shl end
        }
    }.ifEmpty { null }
