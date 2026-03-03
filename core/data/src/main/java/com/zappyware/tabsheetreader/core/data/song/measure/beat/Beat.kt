package com.zappyware.tabsheetreader.core.data.song.measure.beat

import com.zappyware.tabsheetreader.core.data.song.header.Duration
import com.zappyware.tabsheetreader.core.data.song.measure.Voice

data class Beat(
    val voice: Voice,
    val notes: List<Note> = emptyList(),
    val duration: Duration = Duration(),
    val text: String? = null,
    val start: Int? = null,
    val effect: BeatEffect = BeatEffect(),
    val octaves: Octaves = Octaves.None,
    val display: BeatDisplay = BeatDisplay(),
    val status: BeatStatuses = BeatStatuses.Empty,
)

val Beat.startInMeasure: Int? get() = start?.let { it - voice.measure.header.start }

fun Beat.hasVibrato() = notes.any { it.effect.vibrato }

fun Beat.getHarmonic(): HarmonicEffect? = notes.firstOrNull { it.effect.isHarmonic }?.effect?.harmonic
