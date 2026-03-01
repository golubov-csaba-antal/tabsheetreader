package com.zappyware.tabsheetreader.core.data

data class GuitarString(
    val number: Int,
    val value: Int,
)

val NOTES = "C C# D D# E F F# G G# A A# B".split(" ")

val GuitarString.tuning: String get() {
    val semitone = value % 12
    return NOTES[semitone]
}

val GuitarString.note: String get() {
    val octave = value / 12
    val semitone = value % 12
    return "f${NOTES[semitone]}${octave - 1}"
}
