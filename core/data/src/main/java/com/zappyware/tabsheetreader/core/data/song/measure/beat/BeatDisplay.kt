package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BeatDisplay(
    val breakBeam: Boolean = false,
    val forceBeam: Boolean = false,
    val beamDirections: VoiceDirections = VoiceDirections.None,
    val tupletBrackets: TupletBrackets = TupletBrackets.None,
    val breakSecondary: Int = 0,
    val breakSecondaryTuplet: Boolean = false,
    val forceBracket: Boolean = false,
)
