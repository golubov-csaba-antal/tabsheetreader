package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class Note(
    val beat: Beat,
    val value: Int = 0,
    val velocity: Int = Velocities.DEFAULT,
    val string: Int = 0,
    val effect: NoteEffect = NoteEffect(),
    val durationPercent: Float = 1.0f,
    val swapAccidentals: Boolean = false,
    val type: NoteType = NoteType.Rest
) {
    val realValue: Int
        get() = value + beat.voice.measure.track.strings[string - 1].value
}
