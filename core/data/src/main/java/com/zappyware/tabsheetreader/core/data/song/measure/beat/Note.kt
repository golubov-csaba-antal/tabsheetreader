package com.zappyware.tabsheetreader.core.data.song.measure.beat

import com.zappyware.tabsheetreader.core.data.song.measure.Measure

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

    fun getTiedNote(note: Note, measures: List<Measure>): Int {
        val measure = beat.voice.measure
        val voiceIndex = measure.voices.indexOf(beat.voice)

        var tiedNote = -1

        measures.reversed().forEachIndexed { index, measure ->
            val voice = measure.voices[voiceIndex]
            val beats = if (index == 0) voice.beats else voice.beats
            beats.reversed().forEach { beat ->
                if (beat.status != BeatStatuses.Empty) {
                    beat.notes.find { it.string == note.string }?.let {
                        tiedNote = it.value
                        return tiedNote
                    }
                }
            }
        }

        return tiedNote
    }
}

const val TIED_NOTE = -1
