package com.zappyware.tabsheetreader.core.data.song

enum class Durations(val value: Int) {
    WHOLE(1),
    HALF(2),
    QUARTER(4),
    EIGHTH(8),
    SIXTEENTH(16),
    THIRTYSECOND(32),
    SIXTYFOURTH(64),
    HUNDREDTWENTYEIGHTH(128),
}

fun fromTremoloPickingEffect(value: Int) =
    when(value) {
        1 -> Durations.EIGHTH
        2 -> Durations.SIXTEENTH
        3 -> Durations.THIRTYSECOND
        else -> Durations.QUARTER
    }

fun fromTrillPeriod(period: Int) =
    when(period) {
        1 -> Durations.SIXTEENTH
        2 -> Durations.THIRTYSECOND
        3 -> Durations.SIXTYFOURTH
        else -> Durations.QUARTER
    }