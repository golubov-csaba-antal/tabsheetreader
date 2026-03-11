package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamGroup

fun DrawScope.drawBeams(
    beamGroup: BeamGroup,
    color: Color,
    beatOffset: Float,
    verticalOffset: Float,
    beatAreaWidth: Float,
) {
    val beams = beamGroup.beams
    if (beams.isEmpty()) return

    val d = density
    val eighthValue = Durations.EIGHTH.value

    // Cache density-scaled constants
    val strokeWidthLarge = 2 * d
    val strokeWidthSmall = 1 * d
    val beamOffsetAmount = 8 * d
    
    // Optimized vertical spacing:
    // We use a fixed gap and base position to keep all beams below verticalOffset
    // and ensure uniform spacing between subdivision levels.
    val beamGap = 5 * d
    val yBaseBeam = verticalOffset + 20 * d

    var currentX = beatOffset

    for (i in beams.indices) {
        val beam = beams[i]
        val beatDurationValue = beam.beat.duration.value
        val beamLength = beatAreaWidth / beatDurationValue
        val nextX = currentX + beamLength

        val durations = beam.beamDurations
        
        for (j in durations.indices) {
            val dur = durations[j]
            
            // Determine level and type based on the value
            // dur % 8 == 0 -> Full connection to next
            // dur % 8 == 7 -> Forward stub (level = dur + 1)
            // dur % 8 == 6 -> Backward stub (level = dur + 2)
            
            val isFull = dur % 8 == 0
            val isForward = (dur + 1) % 8 == 0
            val isBackward = (dur + 2) % 8 == 0
            
            val level = when {
                isFull -> dur
                isForward -> dur + 1
                isBackward -> dur + 2
                else -> dur
            }
            
            // Map level (8, 16, 32...) to a uniform index (0, 1, 2...)
            // 8 has 3 trailing zeros -> index 0
            // 16 has 4 trailing zeros -> index 1
            val levelIndex = Integer.numberOfTrailingZeros(level) - 3
            val y = yBaseBeam - (levelIndex * beamGap)
            
            val startX: Float
            val endX: Float
            
            when {
                isFull -> {
                    startX = currentX
                    endX = nextX
                }
                isForward -> {
                    startX = currentX
                    endX = currentX + beamOffsetAmount
                }
                isBackward -> {
                    startX = currentX - beamOffsetAmount
                    endX = currentX
                }
                else -> {
                    startX = currentX
                    endX = currentX
                }
            }

            if (startX != endX) {
                drawLine(
                    color = color,
                    start = Offset(startX, y),
                    end = Offset(endX, y),
                    strokeWidth = strokeWidthLarge
                )
            }
        }

        // The stem connects from the top (verticalOffset) down to the primary beam (Level 8).
        // Since Level 8 is index 0 at yBaseBeam (the lowest point), this ensures the stem
        // passes through all subdivision beams.
        val stemEndY = yBaseBeam

        // Draw the vertical beat line (stem)
        drawLine(
            color = color,
            start = Offset(currentX, verticalOffset),
            end = Offset(currentX, stemEndY),
            strokeWidth = strokeWidthSmall
        )

        currentX = nextX
    }
}
