package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BendPoint(
    val position: Int = 0,
    val value: Int = 0,
    val vibrato: Boolean = false
) {
    fun getTime(duration: Long): Int {
        return (duration * position / BendEffect.Companion.MAX_POSITION).toInt()
    }
}
