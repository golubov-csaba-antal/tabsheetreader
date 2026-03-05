package com.zappyware.tabsheetreader.core.data.song.measure.beam

import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStrokeDirections

object BeamHandler {
    // We'll divide the beats by the given time signature into groups
    // e.g. for 4/4, we make groups of beats that does not exceed the 4 quarter duration
    // or for 6/8, we create groups of beams that does not exceed the 6 eight duration
    // we don't need the exact 4 or 6 groups, the duration is important to not exceed
    fun createBeamGroups(
        beats: List<Beat>,
        timeSignature: TimeSignature
    ): List<BeamGroup> {
        // This is the possible maximum duration of a beam group
        // if a group has less duration, we can exceed it one time
        val maxDuration = 1f / timeSignature.denominator.value

        val beamGroups = mutableListOf<BeamGroup>()
        val currentGroup = mutableListOf<Beat>()

        fun addGroupToList() {
            val beams = currentGroup.mapIndexed { index, beat ->
                val next = currentGroup.getOrNull(index + 1)
                val direction = if (next != null) {
                    val cmp = beat.duration.value.compareTo(next.duration.value)
                    // Bitwise mapping:
                    // cmp  1 (Current value > Next value -> Next is LONGER) -> (1 & 1) + (1 ushr 31) = 1 (Up)
                    // cmp -1 (Current value < Next value -> Next is SHORTER) -> (-1 & 1) + (-1 ushr 31) = 2 (Down)
                    // cmp  0 (Equal) -> (0 & 1) + (0 ushr 31) = 0 (None)
                    val ordinal = (cmp and 1) + (cmp ushr 31)
                    BeatStrokeDirections.entries[ordinal]
                } else {
                    BeatStrokeDirections.None
                }
                Beam(beat, direction)
            }
            beamGroups.add(BeamGroup(beams))
        }

        var currentGroupsDuration = 0f

        // if a beat alone has more duration than the maxDuration,
        // we will manually add it to beamGroups and skip adding to currentGroup
        var skipAdd = false
        // if a group has less duration than maxDuration,
        // we can exceed maxDuration once
        var lastChance = false

        beats.forEach { beat ->
            val beatDuration = 1f / beat.duration.value

            if (currentGroupsDuration + beatDuration > maxDuration) {
                if (currentGroup.isEmpty()) {
                    beamGroups.add(BeamGroup(listOf(Beam(beat))))
                    skipAdd = true
                } else if (currentGroupsDuration == maxDuration || lastChance) {
                    addGroupToList()
                    currentGroupsDuration = if (lastChance && currentGroup.isNotEmpty()) (currentGroupsDuration - maxDuration) else 0f
                    currentGroup.clear()
                    lastChance = false
                } else {
                    lastChance = true
                }
            }

            if (skipAdd) {
                skipAdd = false
            } else {
                currentGroup.add(beat)
                currentGroupsDuration += beatDuration
            }
        }

        if (currentGroup.isNotEmpty()) {
            addGroupToList()
        }

        return beamGroups
    }
}
