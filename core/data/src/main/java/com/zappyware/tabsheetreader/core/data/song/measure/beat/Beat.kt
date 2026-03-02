package com.zappyware.tabsheetreader.core.data.song.measure.beat

import com.zappyware.tabsheetreader.core.data.song.header.Duration
import com.zappyware.tabsheetreader.core.data.song.measure.Voice

data class Beat(
    val voice: Voice,
    val notes: List<Note>,
    val duration: Duration,
    val text: String?,
    val start: Int?,
    val effect: BeatEffect,
    val octaves: Octaves,
    val display: BeatDisplay,
    val statuses: BeatStatuses,
)

val Beat.startInMeasure: Int? get() = start?.let { it - voice.measure.header.start }

fun Beat.hasVibrato() = notes.any { it.effect.vibrato }

fun Beat.getHarmonic(): HarmonicEffect? = notes.firstOrNull { it.effect.isHarmonic }?.effect?.harmonic
