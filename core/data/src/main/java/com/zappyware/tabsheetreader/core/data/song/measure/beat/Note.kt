package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class Note(
    val value: Int = 0,
    val velocity: Int = Velocities.DEFAULT,
    val string: Int = 0,
    val effect: NoteEffect = NoteEffect(),
    val durationPercent: Float = 1.0f,
    val swapAccidentals: Boolean = false,
    val type: NoteType = NoteType.Rest
)

const val TIED_NOTE = -1
