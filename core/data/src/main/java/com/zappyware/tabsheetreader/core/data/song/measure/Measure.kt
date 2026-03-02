package com.zappyware.tabsheetreader.core.data.song.measure

import com.zappyware.tabsheetreader.core.data.song.header.MeasureHeader
import com.zappyware.tabsheetreader.core.data.song.track.Track

data class Measure(
    val header: MeasureHeader,
    val track: Track,
    val clef: MeasureClef,
    val lineBreak: LineBreak,
    val voices: List<Voice>,
)

const val MaxVoicesInMeasure = 2

fun Measure.isEmpty() = voices.all { it.isEmpty() }
