package com.zappyware.tabsheetreader.core.data.song.measure.beam

import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat

data class Beam(
    // the beat inside this beam
    val beat: Beat,
    // durations that should be drawn for this beam
    // e.g. [8, 16] means an eighth and a sixteenth beam should be drawn
    // a value ending in 5 means a "short" beam (stub), e.g. 15 is a short 16th beam
    val beamDurations: IntArray = intArrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Beam
        if (beat != other.beat) return false
        if (!beamDurations.contentEquals(other.beamDurations)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = beat.hashCode()
        result = 31 * result + beamDurations.contentHashCode()
        return result
    }
}
