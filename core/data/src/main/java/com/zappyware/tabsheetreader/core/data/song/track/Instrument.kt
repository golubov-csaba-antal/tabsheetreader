package com.zappyware.tabsheetreader.core.data.song.track

data class Instrument(
    val instrument: Int = -1,
    val unknown: Int = -1,
    val soundBank: Int = -1,
    val effectNumber: Int = -1,
) {
    companion object {
        val Default = Instrument()
    }
}
