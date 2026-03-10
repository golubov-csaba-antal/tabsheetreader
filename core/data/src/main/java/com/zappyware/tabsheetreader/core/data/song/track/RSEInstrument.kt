package com.zappyware.tabsheetreader.core.data.song.track

data class RSEInstrument(
    val instrument: Int = -1,
    val unknown: Int = -1,
    val soundBank: Int = -1,
    val effectNumber: Int = -1,
    val effectCategory: String = "",
    val effect: String = "",
) {
    companion object {
        val Default = RSEInstrument()
    }
}