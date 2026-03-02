package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class ArtificialHarmonic(
    val pitch: PitchClass? = null,
    val octave: Int? = null
) : HarmonicEffect(2)
