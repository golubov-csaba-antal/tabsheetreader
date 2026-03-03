package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BeatEffect(
    val stroke: BeatStroke = BeatStroke(),
    val hasRasgueado: Boolean = false,
    val pickStroke: BeatStrokeDirections = BeatStrokeDirections.None,
    val chord: Chord? = null,
    val fadeIn: Boolean = false,
    val tremoloBar: BendEffect? = null,
    val mixTableChange: MixTableChange? = null,
    val slapEffects: SlapEffects = SlapEffects.None,
    val vibrato: Boolean = false,
) {
    companion object {
        val NONE = BeatEffect()
    }
}

fun BeatEffect.isChord() = chord != Chord.None

fun BeatEffect.isTremoloBar() = tremoloBar != BendEffect.None

fun BeatEffect.isSlapEffects() = slapEffects != SlapEffects.None

fun BeatEffect.hasPickStroke() = pickStroke != BeatStrokeDirections.None
