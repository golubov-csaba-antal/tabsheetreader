package com.zappyware.tabsheetreader.core.data.song.header

data class TimeSignature(
    val numerator: Int = 4,
    val denominator: Duration,
) {
    companion object {
        val Default = TimeSignature(4, Duration.Default)
    }
}
