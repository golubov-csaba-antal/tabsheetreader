package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BeatDisplay(
    val breakBeam: Boolean,
    val forceBeam: Boolean,
    val beamDirections: VoiceDirections,
    val tupletBrackets: TupletBrackets,
    val breakSecondary: Int,
    val breakSecondaryBracket: Boolean,
    val forceBracket: Boolean,
)
