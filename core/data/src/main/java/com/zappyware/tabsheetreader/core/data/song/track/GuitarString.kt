package com.zappyware.tabsheetreader.core.data.song.track

data class GuitarString(
    val number: Int,
    val value: Int,
) {
    companion object {
        val Default: List<GuitarString> = listOf(
            GuitarString(1, 64),
            GuitarString(2, 59),
            GuitarString(3, 55),
            GuitarString(4, 50),
            GuitarString(5, 45),
            GuitarString(6, 40),
        )
    }
}

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
