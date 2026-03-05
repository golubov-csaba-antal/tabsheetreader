package com.zappyware.tabsheetreader.core.data.song

data class Clipboard(
    val startMeasure: Int = 1,
    val stopMeasure: Int = 1,
    val startTrack: Int = 1,
    val stopTrack: Int = 1,
    val startBeat: Int = 1,
    val stopBeat: Int = 1,
    val subBarCopy: Boolean = false,
)
