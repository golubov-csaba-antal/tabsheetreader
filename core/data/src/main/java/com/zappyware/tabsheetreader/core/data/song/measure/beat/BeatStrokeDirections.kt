package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class BeatStrokeDirections {
    None,
    Up,
    Down,
}

fun findBeatStrokeDirection(direction: Int) =
    BeatStrokeDirections.entries.find { it.ordinal == direction } ?: BeatStrokeDirections.None