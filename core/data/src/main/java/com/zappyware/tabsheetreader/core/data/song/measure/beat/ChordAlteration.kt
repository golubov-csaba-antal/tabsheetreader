package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class ChordAlteration {
    Perfect,
    Diminished,
    Augmented
}

fun findChordAlteration(value: Int) = ChordAlteration.entries.firstOrNull { it.ordinal == value }
