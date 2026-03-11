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

    val textMeasurer = rememberTextMeasurer()
    
    val headerTextStyle = typography.headlineSmall
    val timeSignatureTextStyle = typography.displayMedium
    val repeatCloseTextStyle = typography.labelSmall
    val beatTextStyle = typography.bodySmall
    val musicalTextStyle = typography.bodyMedium

    val backgroundColor = MaterialTheme.colorScheme.background
    val drawColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    val voice = measure.voices.firstOrNull()

    // Pre-calculate layouts for everything that requires text measurement
    val repeatCloseLayout = remember(repeatClose, drawColor) {
        if (repeatClose > 0) textMeasurer.measure("${repeatClose}x", repeatCloseTextStyle.copy(color = drawColor)) else null
    }

    val repeatAlternativesLayout = remember(repeatAlternatives, drawColor) {
        if (!repeatAlternatives.isNullOrEmpty()) textMeasurer.measure(repeatAlternatives, repeatCloseTextStyle.copy(color = drawColor)) else null
    }

    val timeSignatureLayouts = remember(timeSignature, drawColor) {
        timeSignature?.let {
            textMeasurer.measure("${it.numerator}", timeSignatureTextStyle.copy(color = drawColor)) to
            textMeasurer.measure("${it.denominator.value}", timeSignatureTextStyle.copy(color = drawColor))
        }
    }

    val pmLayout = remember(drawColor) {
        textMeasurer.measure("PM", headerTextStyle.copy(color = drawColor))
    }
    
    val pmDashLayout = remember(drawColor) {
        textMeasurer.measure("\u2014", headerTextStyle.copy(color = drawColor))
    }

    val beatLayouts = remember(voice, drawColor) {
        voice?.beats?.map { beat ->
            val notes = beat.notes
            if (notes.isEmpty()) {
                val text = MusicalCharacters.getRestCharacter(beat.duration.value)
                listOf(textMeasurer.measure(text, musicalTextStyle.copy(color = drawColor)))
            } else {
                notes.map { note ->
                    val (text, style) = when (note.type) {
                        NoteType.Rest -> MusicalCharacters.getRestCharacter(beat.duration.value) to musicalTextStyle
                        NoteType.Dead -> MusicalCharacters.getDeadNoteCharacter() to beatTextStyle
                        else -> (if (note.value == TIED_NOTE) "T" else note.value.toString()) to beatTextStyle
                    }
                    textMeasurer.measure(text, style.copy(color = drawColor))
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
        val p2 = 2 * d
        val p6 = 3 * d
        val p8 = 4 * d
        val p26 = 13 * d
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
                repeatCloseLayout?.let {
                    drawRepeatTimes(it, drawColor)
                }
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

        repeatAlternativesLayout?.let {
            drawText(textLayoutResult = it, topLeft = Offset(size.width / 2f - it.size.width / 2f, 0f), color = drawColor)
        }

        timeSignatureLayouts?.let { (num, den) ->
            drawTimeSignature(
                numeratorLayout = num,
                denominatorLayout = den,
                horizontalSpace = yOffset,
                stringCount = stringCount,
                stringDistance = yOffset,
                drawColor = drawColor,
            )
        }

        val beatAreaWidth = size.width - p80
        val beats = voice?.beats
        var pmActive = false
        
        if (beats != null) {
            var currentBeatOffset = drawStartingOffsetX
            for (index in beats.indices) {
                val beat = beats[index]
                drawBeat(
                    beat = beat,
                    textMeasurer = textMeasurer,
                    textStyle = beatTextStyle, // This is just a fallback now
                    backgroundColor = backgroundColor,
                    color = drawColor,
                    beatOffset = currentBeatOffset,
                    stringOffset = yOffset,
                    cachedLayouts = beatLayouts.getOrNull(index)
                )

                if (beat.hasPalmMute()) {
                    val pmLayoutToUse = if (pmActive) pmDashLayout else pmLayout
                    drawPalmMutes(
                        yOffset = stringCount * yOffset + p80,
                        currentBeatOffset = currentBeatOffset,
                        color = drawColor,
                        pmLayoutResult = pmLayoutToUse,
                        noteLayoutResult = beatLayouts.getOrNull(index)?.firstOrNull(),
                    )
                    pmActive = true
                } else {
                    pmActive = false
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
