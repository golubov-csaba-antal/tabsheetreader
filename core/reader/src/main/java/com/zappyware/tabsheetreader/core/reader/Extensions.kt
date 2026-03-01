package com.zappyware.tabsheetreader.core.reader

import java.io.InputStream
import java.lang.Integer.max
import java.lang.Integer.min
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

fun Int.toRepeatAlternatives(): String? {
    // shortcut to handle 0 without any further processing
    if (this == 0) return null
    // make an 8-long string representation of the bits
    // then reverse it to get the correct order
    // e.g. 7 is 00000111, it'll be reversed to 11100000
    // because the 1st, 2nd and 3rd repeat ordinal is set
    val string = toString(2)
        .padStart(8, '0')
        .reversed()
    // we have to get all the bits' index where 1 is set
    // and convert index to the ordinal number which starts from 1
    val repeatIndicies = string.toCharArray()
        .mapIndexed { index, ch -> if (ch == '1') index + 1 else null }
        .filterNotNull()
    // if no ordinal number is set (=the bits weren't set), return null
    // the shortcut above handles it, but I leave this here just in case
    if(repeatIndicies.isEmpty()) return null
    // otherwise, we need to convert the consequent numbers to ranges
    // or add any non-consequent number separated with a comma
    // e.g.
    // [1] will be converted to "1"
    // [1, 2] will be converted to "1-2"
    // [1, 2, 3] will be converted to "1-3"
    // [1, 2, 3, 7] will be converted to "1-3, 7"
    return repeatIndicies
        .fold("") { acc, i ->
            if (acc.isEmpty()) {
                // first number is just returned
                "$i"
            } else if (acc.last().digitToInt() + 1 == i) {
                // this is a consequent number!
                if (acc.getOrNull(acc.count() - 2) != '-') {
                    // the consequent number attached to make a range
                    "$acc-$i"
                } else {
                    // or any further consequent number is added into the range's end
                    acc.dropLast(1) + "$i"
                }
            } else {
                // any non-consequent number is attached with a comma
                "$acc, $i"
            }
        }
    }
