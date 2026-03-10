package com.zappyware.tabsheetreader.core.data.song.measure.beam

import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat

object BeamHandler {
    fun createBeamGroups(
        beats: List<Beat>,
        timeSignature: TimeSignature
    ): List<BeamGroup> {
        val numerator = timeSignature.numerator
        val denominatorVal = timeSignature.denominator.value
        
        // Musical grouping logic:
        // 1. If numerator is multiple of 3 and denominator is 8 (6/8, 9/8, 12/8), 
        //    group by 3 eighth notes (dotted quarter).
        // 2. Otherwise, group by the denominator unit.
        val groupingMultiplier = if (numerator % 3 == 0 && numerator > 3 && denominatorVal == 8) 3 else 1
        val maxDuration = groupingMultiplier.toFloat() / denominatorVal

        val beamGroups = mutableListOf<BeamGroup>()
        val currentGroup = mutableListOf<Beat>()
        var currentGroupsDuration = 0f

        fun addGroupToList() {
            if (currentGroup.isEmpty()) return
            
            val beams = currentGroup.mapIndexed { index, beat ->
                val prev = currentGroup.getOrNull(index - 1)
                val next = currentGroup.getOrNull(index + 1)
                
                val beamDurations = mutableListOf<Int>()
                var d = 8
                while (d <= beat.duration.value) {
                    if (next != null && next.duration.value >= d) {
                        // Connect to next beat at this level
                        beamDurations.add(d)
                    } else if (d > 8) {
                        // Stub logic
                        if (index > 0 && (next == null || (prev != null && prev.duration.value >= d))) {
                            beamDurations.add(d - 2) // Backward stub (e.g. 14, 30)
                        } else {
                            beamDurations.add(d - 1) // Forward stub (e.g. 15, 31)
                        }
                    }
                    d *= 2
                }
                
                Beam(beat, beamDurations.toIntArray())
            }
            beamGroups.add(BeamGroup(beams))
        }

        for (i in beats.indices) {
            val beat = beats[i]
            val beatDuration = 1f / beat.duration.value

            // If adding this beat exceeds the limit, check if we should still add it to the current group
            if (currentGroupsDuration + beatDuration > maxDuration + 0.001f) {
                
                // If this is the last remaining beat or sequence in the measure,
                // and its duration is small (less than a quarter), 
                // don't start a new group, just merge it with the current one.
                var remainingDuration = 0f
                for (j in i until beats.size) {
                    remainingDuration += 1f / beats[j].duration.value
                }

                if (remainingDuration < maxDuration - 0.001f) {
                    // Do nothing, let it fall through and be added to the current group
                } else {
                    addGroupToList()
                    currentGroup.clear()
                    currentGroupsDuration = 0f
                }
            }

            currentGroup.add(beat)
            currentGroupsDuration += beatDuration
        }

        addGroupToList()
        return beamGroups
    }
}
