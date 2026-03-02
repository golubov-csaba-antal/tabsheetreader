package com.zappyware.tabsheetreader.core.data.song.header

enum class TripletFeel {
    NONE,
    EIGHT,
    SIXTEENTH,
}

fun findTripletFeel(value: Int) = TripletFeel.entries.find { it.ordinal == value } ?: TripletFeel.NONE
