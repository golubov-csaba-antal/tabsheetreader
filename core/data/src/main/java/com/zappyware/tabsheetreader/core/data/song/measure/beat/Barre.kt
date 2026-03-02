package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class Barre(
    val fret: Int,
    val start: Int = 0,
    val end: Int = 0
) {
    val range: Pair<Int, Int>
        get() = start to end
}
