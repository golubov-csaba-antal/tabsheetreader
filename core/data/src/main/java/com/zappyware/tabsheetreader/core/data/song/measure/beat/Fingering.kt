package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class Fingering(val value: Int) {
    Open(-1),
    Thumb(0),
    Index(1),
    Middle(2),
    Annular(3),
    Little(4),
}

fun findFingering(value: Int) = Fingering.entries.find { it.value == value } ?: Fingering.Open
