package com.zappyware.tabsheetreader.core.data

enum class KeySignatures(
    val enters: Int,
    val times: Int
) {
    FMajorFlat(-8, 0),
    CMajorFlat(-7, 0),
    GMajorFlat(-6, 0),
    DMajorFlat(-5, 0),
    AMajorFlat(-4, 0),
    EMajorFlat(-3, 0),
    BMajorFlat(-2, 0),
    FMajor(-1, 0),
    CMajor(0, 0),
    GMajor(1, 0),
    DMajor(2, 0),
    AMajor(3, 0),
    EMajor(4, 0),
    BMajor(5, 0),
    FMajorSharp(6, 0),
    CMajorSharp(7, 0),
    GMajorSharp(8, 0),

    DMinorFlat(-8, 1),
    AMinorFlat(-7, 1),
    EMinorFlat(-6, 1),
    BMinorFlat(-5, 1),
    FMinor(-4, 1),
    CMinor(-3, 1),
    GMinor(-2, 1),
    DMinor(-1, 1),
    AMinor(0, 1),
    EMinor(1, 1),
    BMinor(2, 1),
    FMinorSharp(3, 1),
    CMinorSharp(4, 1),
    GMinorSharp(5, 1),
    DMinorSharp(6, 1),
    AMinorSharp(7, 1),
    EMinorSharp(8, 1),
}

fun findKeySignatures(enters: Int, times: Int) = KeySignatures.entries.find { it.enters == enters && it.times == times } ?: KeySignatures.CMajor
