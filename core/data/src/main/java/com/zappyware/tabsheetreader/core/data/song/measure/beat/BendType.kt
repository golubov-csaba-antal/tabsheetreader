package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class BendType {
    None,
    Bend,
    BendRelease,
    BendReleaseBend,
    Prebend,
    PrebendRelease,
    Dip,
    Dive,
    ReleaseUp,
    InvertedDip,
    Return,
    ReleaseDown
}

fun findBendType(value: Int): BendType =
    BendType.entries.find { it.ordinal == value } ?: BendType.None
