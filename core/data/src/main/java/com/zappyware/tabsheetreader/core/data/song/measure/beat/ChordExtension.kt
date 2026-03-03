package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class ChordExtension {
    None,
    Ninth,
    Eleventh,
    Thirteenth
}

fun findChordExtension(value: Int) = ChordExtension.entries.firstOrNull { it.ordinal == value }
