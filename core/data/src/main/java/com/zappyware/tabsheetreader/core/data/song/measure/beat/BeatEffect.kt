package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BeatEffect(
    val stroke: BeatStroke,
    val hasRasgueado: Boolean,
    val pickStroke: BeatStrokeDirections,
    val chord: Chord?,
    val fadeIn: Boolean,
    val tremoloBar: BendEffect?,
    val mixTableChange: MixTableChange?,
    val slapEffects: SlapEffects,
    val vibrato: Boolean,
)

fun BeatEffect.isChord() = chord != Chord.None

fun BeatEffect.isTremoloBar() = tremoloBar != BendEffect.None

fun BeatEffect.isSlapEffects() = slapEffects != SlapEffects.None

fun BeatEffect.hasPickStroke() = pickStroke != BeatStrokeDirections.None
