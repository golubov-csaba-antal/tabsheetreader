package com.zappyware.tabsheetreader.core.data.song.measure.beam

import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStrokeDirections

data class Beam(
    // the beat inside this beam
    val beat: Beat,
    // the direction whether the next beat is longer or shorter
    val direction: BeatStrokeDirections = BeatStrokeDirections.None,
)
