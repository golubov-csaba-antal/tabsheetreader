package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class BeatStatuses {
    Empty,
    Normal,
    Rest,
}

fun findBeatStatuses(value: Int): BeatStatuses =
    BeatStatuses.entries.find { it.ordinal == value } ?: BeatStatuses.Empty
