package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class BeatStroke(
    val value: Int = 0,
    val direction: BeatStrokeDirections = BeatStrokeDirections.None,
) {
    companion object {
        val NONE = BeatStroke()
    }
}

fun BeatStroke.swapDirections(): BeatStroke =
    if (direction == BeatStrokeDirections.Up) {
        copy(direction = BeatStrokeDirections.Down)
    } else if (direction == BeatStrokeDirections.Down) {
        copy(direction = BeatStrokeDirections.Up)
    } else {
        this
    }
