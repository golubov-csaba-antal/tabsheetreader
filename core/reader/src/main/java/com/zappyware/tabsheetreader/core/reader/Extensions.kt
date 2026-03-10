package com.zappyware.tabsheetreader.core.reader

import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Velocities
import java.io.InputStream
import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Integer.numberOfTrailingZeros

fun InputStream.readBoolean(): Boolean {
    return read() != 0
}

/** Read a signed 8-bit integer (-128 to 127) */
fun InputStream.readI8(): Int = read().toByte().toInt()

/** Read an unsigned 8-bit integer (0 to 255) */
fun InputStream.readU8(): Int = read()

private fun InputStream.readFully(bytes: ByteArray, count: Int) {
    var totalRead = 0
    while (totalRead < count) {
        val readCount = read(bytes, totalRead, count - totalRead)
        if (readCount == -1) break
        totalRead += readCount
    }
}

fun InputStream.readI16(): Int {
    val b1 = read()
    val b2 = read()
    if (b1 == -1 || b2 == -1) return 0
    return ((b2 shl 8) or (b1 and 0xFF)).toShort().toInt()
}

fun InputStream.readI32(): Int {
    val b1 = read()
    val b2 = read()
    val b3 = read()
    val b4 = read()
    if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1) return 0
    return (b4 shl 24) or ((b3 and 0xFF) shl 16) or ((b2 and 0xFF) shl 8) or (b1 and 0xFF)
}

fun InputStream.readF64(): Double {
    val b1 = read().toLong()
    val b2 = read().toLong()
    val b3 = read().toLong()
    val b4 = read().toLong()
    val b5 = read().toLong()
    val b6 = read().toLong()
    val b7 = read().toLong()
    val b8 = read().toLong()
    if (b1 == -1L || b2 == -1L || b3 == -1L || b4 == -1L || b5 == -1L || b6 == -1L || b7 == -1L || b8 == -1L) return 0.0
    val bits = (b8 shl 56) or
            ((b7 and 0xFF) shl 48) or
            ((b6 and 0xFF) shl 40) or
            ((b5 and 0xFF) shl 32) or
            ((b4 and 0xFF) shl 24) or
            ((b3 and 0xFF) shl 16) or
            ((b2 and 0xFF) shl 8) or
            (b1 and 0xFF)
    return java.lang.Double.longBitsToDouble(bits)
}

fun InputStream.readByteSizeString(count: Int): String {
    val actualSize = read()
    if (count <= 0) return ""

    val bytes = ByteArray(count)
    readFully(bytes, count)
    return if (actualSize <= 0 || (actualSize == 1 && bytes[0] == 0.toByte())) "" else String(bytes, 0, min(actualSize, count))
}

fun InputStream.readIByteSizeString(): String {
    val size = readI32()
    return readByteSizeString(size - 1)
}

fun InputStream.readIntSizeString(): String {
    val size = readI32()
    if (size <= 0) return ""
    val bytes = ByteArray(size)
    readFully(bytes, size)
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
            val startBit = numberOfTrailingZeros(mask)
            val start = startBit + 1
            val shifted = mask ushr startBit
            val runLength = numberOfTrailingZeros(shifted.inv())
            val end = startBit + runLength

            if (isNotEmpty()) append(", ")
            if (runLength == 1) {
                append(start)
            } else {
                append(start).append('-').append(end)
            }
            mask = if (end >= 32) 0 else (mask ushr end) shl end
        }
    }.ifEmpty { null }

fun Int.toVelocity(): Int =
    Velocities.MIN_VELOCITY + Velocities.VELOCITY_INCREMENT * this - Velocities.VELOCITY_INCREMENT

fun Int.toStroke(): Int =
    when(this) {
        1 -> Durations.HUNDREDTWENTYEIGHTH.value
        2 -> Durations.SIXTYFOURTH.value
        3 -> Durations.THIRTYSECOND.value
        4 -> Durations.SIXTEENTH.value
        5 -> Durations.EIGHTH.value
        6 -> Durations.QUARTER.value
        else -> Durations.SIXTYFOURTH.value
    }
