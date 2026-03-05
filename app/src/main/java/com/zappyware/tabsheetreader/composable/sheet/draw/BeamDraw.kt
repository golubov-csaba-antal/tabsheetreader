package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamGroup
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStrokeDirections

fun DrawScope.drawBeams(
    beamGroup: BeamGroup,
    color: Color,
    beatOffset: Float,
    verticalOffset: Float,
    beatAreaWidth: Float,
) {
    val beams = beamGroup.beams
    if (beams.isEmpty()) return

    // Pre-calculate density once to avoid repeated property lookups and conversions
    val d = density
    val eighthValue = Durations.EIGHTH.value

    // Cache density-scaled constants
    val strokeWidthLarge = 2 * d
    val strokeWidthSmall = 1 * d
    val beamOffsetAmount = 4 * d
    val yBase = verticalOffset + 24 * d

    val stemEndY = yBase - (eighthValue * d)

    var currentX = beatOffset

    // Use for-loop to avoid iterator allocation per beam group
    for (i in beams.indices) {
        val beam = beams[i]
        val beatDurationValue = beam.beat.duration.value
        val beamLength = beatAreaWidth / beatDurationValue
        val nextX = currentX + beamLength

        // Draw horizontal beams/flags
        if (i < beams.lastIndex) {
            var dValue = eighthValue
            var dPx = eighthValue * d
            while (dValue <= beatDurationValue) {
                val y = yBase - dPx
                
                val startX = if (beam.direction == BeatStrokeDirections.Down && dValue > eighthValue) {
                    nextX - beamOffsetAmount
                } else {
                    currentX
                }
                
                val endX = if (beam.direction == BeatStrokeDirections.Up && dValue > eighthValue) {
                    currentX + beamOffsetAmount
                } else {
                    nextX
                }

                // drawLine is faster than Path allocation + JNI lineTo calls for these segments
                drawLine(
                    color = color,
                    start = Offset(startX, y),
                    end = Offset(endX, y),
                    strokeWidth = strokeWidthLarge
                )
                
                dValue *= 2
                dPx *= 2
            }
        }

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
