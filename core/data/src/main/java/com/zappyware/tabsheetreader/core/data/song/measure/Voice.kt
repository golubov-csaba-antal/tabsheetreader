package com.zappyware.tabsheetreader.core.data.song.measure

import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamGroup
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.VoiceDirections

data class Voice(
    val measure: Measure,
    val beats: List<Beat> = emptyList(),
    val beamGroups: List<BeamGroup> = emptyList(),
    val directions: VoiceDirections = VoiceDirections.None,
)

fun Voice.isEmpty() = beats.isEmpty()
