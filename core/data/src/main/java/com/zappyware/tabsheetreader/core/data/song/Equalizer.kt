package com.zappyware.tabsheetreader.core.data.song

data class Equalizer(
    val knobsVolume: List<Float>,
    val gain: Float = 0f,
) {
    companion object {
        val Default = Equalizer(emptyList(), 0f)
    }
}
