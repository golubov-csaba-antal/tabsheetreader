package com.zappyware.tabsheetreader.core.data.song.measure.beat

import com.zappyware.tabsheetreader.core.data.song.Octave

data class ArtificialHarmonic(
    val pitch: PitchClass? = null,
    val octave: Octave? = null
) : HarmonicEffect(2)
