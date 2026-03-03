package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BendEffect(
    val type: BendType = BendType.None,
    val value: Int = 0,
    val points: List<BendPoint> = emptyList()
) {
    companion object {
        const val SEMITONE_LENGTH = 1
        const val MAX_POSITION = 12
        const val MAX_VALUE = SEMITONE_LENGTH * 12

        val None = BendEffect()
    }
}

fun BendEffect.isEmpty() = points.isEmpty()
