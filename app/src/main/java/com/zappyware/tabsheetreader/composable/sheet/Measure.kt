package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.zappyware.tabsheetreader.MusicalCharacters
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeams
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeat
import com.zappyware.tabsheetreader.composable.sheet.draw.drawMeasureHeader
import com.zappyware.tabsheetreader.composable.sheet.draw.drawPalmMutes
import com.zappyware.tabsheetreader.composable.sheet.draw.drawRepeat
import com.zappyware.tabsheetreader.composable.sheet.draw.drawRepeatOpen
import com.zappyware.tabsheetreader.composable.sheet.draw.drawRepeatTimes
import com.zappyware.tabsheetreader.composable.sheet.draw.drawSongClosure
import com.zappyware.tabsheetreader.composable.sheet.draw.drawStrings
import com.zappyware.tabsheetreader.composable.sheet.draw.drawTimeSignature
import com.zappyware.tabsheetreader.composable.sheet.draw.drawVerticalLine
import com.zappyware.tabsheetreader.core.data.song.measure.Measure
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TIED_NOTE
import com.zappyware.tabsheetreader.core.data.song.measure.beat.hasPalmMute
import com.zappyware.tabsheetreader.core.data.song.track.Track

@Composable
fun Measure(
    measure: Measure,
    measureCount: Int,
    selectedTrack: Track?,
    stringCount: Int,
    modifier: Modifier,
    typography: Typography,
) {
    val measureIndex = measure.header.number
    val measureTitle = measure.header.marker?.title
    val timeSignature = if (measure.header.timeSignatureChanged) measure.header.timeSignature else null
    val isLastMeasure = measure.header.number == measureCount
    val isRepeatOpen = measure.header.isRepeatOpen
    val repeatClose = measure.header.repeatClose
    val repeatAlternatives = measure.header.repeatAlternatives

    val headerText = remember(measureIndex, measureTitle) {
        if (measureTitle.isNullOrEmpty()) "$measureIndex" else "$measureIndex - $measureTitle"
    }

    // A single text measurer can handle all text layouts
    val textMeasurer = rememberTextMeasurer()
    
    val headerTextStyle = typography.headlineSmall
    val timeSignatureTextStyle = typography.displayMedium
    val repeatCloseTextStyle = typography.labelSmall
    val beatTextStyle = typography.bodySmall
    val musicalTextStyle = typography.bodyMedium

    val backgroundColor = MaterialTheme.colorScheme.background
    val drawColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    val voice = measure.voices.firstOrNull()

    val textStyles = mutableListOf<TextStyle>()
    
    // Pre-calculate text layouts for beats to avoid measuring during Draw phase
    val beatLayouts = remember(voice, drawColor) {
        textStyles.clear()
        voice?.beats?.map { beat ->
            val notes = beat.notes
            if (notes.isEmpty()) {
                val text = MusicalCharacters.getRestCharacter(beat.duration.value)
                textStyles.add(musicalTextStyle)
                listOf(textMeasurer.measure(text, musicalTextStyle.copy(color = drawColor)))
            } else {
                notes.map { note ->
                    when (note.type) {
                        NoteType.Rest -> {
                            val text = MusicalCharacters.getRestCharacter(beat.duration.value)
                            textStyles.add(musicalTextStyle)
                            textMeasurer.measure(text, musicalTextStyle.copy(color = drawColor))
                        }
                        NoteType.Dead -> {
                            val text = MusicalCharacters.getDeadNoteCharacter()
                            textStyles.add(beatTextStyle)
                            textMeasurer.measure(text, beatTextStyle.copy(color = drawColor))
                        }
                        else -> {
                            val text = if (note.value == TIED_NOTE) {
                                "T"
                            } else {
                                note.value.toString()
                            }
                            textStyles.add(beatTextStyle)
                            textMeasurer.measure(text, beatTextStyle.copy(color = drawColor))
                        }
                    }
                }
            }
        } ?: emptyList()
    }

    Canvas(
        modifier = modifier,
    ) {
        val d = density
        val yOffset = 148 * d / 10f

        drawMeasureHeader(headerText, textMeasurer, headerTextStyle, drawColor)
        drawStrings(stringCount, yOffset, drawColor)

        // Hoist density-scaled constants
        val p2 = 1 * d
        val p6 = 3 * d
        val p8 = 4 * d
        val p26 = 13 * d
        val p32 = 16 * d
        val p40 = 20 * d
        val p60 = 30 * d
        val p80 = 40 * d
        val drawStartingOffsetX = if (measure.header.timeSignatureChanged) p80 else if (isRepeatOpen) p60 else p40

        if (isRepeatOpen) {
            drawRepeatOpen(
                stringCount = stringCount,
                stringDistance = yOffset,
                drawColor = drawColor,
                thickLineWidth = p8,
                thinLineWidth = p2,
                circleRadius = p6,
            )
        } else {
            drawVerticalLine(
                stringCount = stringCount,
                startingOffset = 0f,
                stringDistance = yOffset,
                drawColor = drawColor,
                lineWidth = p2,
            )
        }

        if (isLastMeasure || repeatClose > 0) {
            drawSongClosure(
                stringCount = stringCount,
                stringDistance = yOffset,
                drawColor = drawColor,
                thickLineWidth = p8,
                thinLineWidth = p2,
            )

            if (repeatClose > 0) {
                drawRepeat(
                    stringCount = stringCount,
                    stringDistance = yOffset,
                    drawColor = drawColor,
                    thickLineWidth = p8,
                    thinLineWidth = p2,
                    circleRadius = p6,
                )
                drawRepeatTimes(
                    repeatClose,
                    textMeasurer,
                    repeatCloseTextStyle,
                    drawColor,
                )
            }
        } else {
            drawVerticalLine(
                stringCount = stringCount,
                startingOffset = size.width,
                stringDistance = yOffset,
                drawColor = drawColor,
                lineWidth = p2,
            )
        }

        if (!repeatAlternatives.isNullOrEmpty()) {
            drawText(textMeasurer = textMeasurer, text = repeatAlternatives, topLeft = Offset(size.width / 2f - p32, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
        }

        drawTimeSignature(
            timeSignature = timeSignature,
            textMeasurer = textMeasurer,
            timeSignatureTextStyle = timeSignatureTextStyle,
            horizontalSpace = yOffset,
            stringCount = stringCount,
            stringDistance = yOffset,
            drawColor = drawColor,
        )

        val beatAreaWidth = size.width - p80
        val beats = voice?.beats
        var drawnPalmMute = false
        
        if (beats != null) {
            var currentBeatOffset = drawStartingOffsetX
            for (index in beats.indices) {
                val beat = beats[index]
                drawBeat(
                    beat = beat,
                    textMeasurer = textMeasurer,
                    textStyle = textStyles.getOrNull(index) ?: beatTextStyle,
                    backgroundColor = backgroundColor,
                    color = drawColor,
                    beatOffset = currentBeatOffset,
                    stringOffset = yOffset,
                    cachedLayouts = beatLayouts.getOrNull(index)
                )

                if (beat.hasPalmMute()) {
                    drawPalmMutes(
                        yOffset = stringCount * yOffset + p80,
                        currentBeatOffset = currentBeatOffset,
                        headerTextMeasurer = textMeasurer,
                        headerTextStyle = headerTextStyle,
                        color = drawColor,
                        drawnPalmMute = drawnPalmMute,
                        layoutResult = beatLayouts.getOrNull(index)?.firstOrNull(),
                    )
                    drawnPalmMute = true
                } else {
                    drawnPalmMute = false
                }

                currentBeatOffset += beatAreaWidth / beat.duration.value
            }
        }

        val beamGroups = voice?.beamGroups
        if (beamGroups != null) {
            var currentBeamOffset = drawStartingOffsetX
            val verticalLineOffset = stringCount * yOffset + p26
            
            for (i in beamGroups.indices) {
                val group = beamGroups[i]
                drawBeams(
                    beamGroup = group,
                    color = drawColor,
                    beatOffset = currentBeamOffset,
                    verticalOffset = verticalLineOffset,
                    beatAreaWidth = beatAreaWidth,
                )
                
                // Optimized group offset calculation without fold/lambda
                val groupBeams = group.beams
                var groupDurationSum = 0f
                for (j in groupBeams.indices) {
                    groupDurationSum += 1f / groupBeams[j].beat.duration.value
                }
                currentBeamOffset += beatAreaWidth * groupDurationSum
            }
        }
    }
}
