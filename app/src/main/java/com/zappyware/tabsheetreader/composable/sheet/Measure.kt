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
import androidx.compose.ui.unit.dp
import com.zappyware.tabsheetreader.MusicalCharacters
import com.zappyware.tabsheetreader.composable.common.changeFontSizeForMeasure
import com.zappyware.tabsheetreader.composable.common.complexMeasureStyleMultiplier
import com.zappyware.tabsheetreader.composable.common.isComplex
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeams
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeat
import com.zappyware.tabsheetreader.composable.sheet.draw.drawMeasureHeader
import com.zappyware.tabsheetreader.composable.sheet.draw.drawPalmMutes
import com.zappyware.tabsheetreader.composable.sheet.draw.drawStrings
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
    
    val headerTextStyle = typography.headlineSmall.changeFontSizeForMeasure(measure)
    val timeSignatureTextStyle = typography.displayMedium.changeFontSizeForMeasure(measure)
    val repeatCloseTextStyle = typography.labelSmall.changeFontSizeForMeasure(measure)
    val beatTextStyle = typography.bodySmall.changeFontSizeForMeasure(measure)
    val musicalTextStyle = typography.bodyMedium.changeFontSizeForMeasure(measure)

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
        val yOffset = size.height / 10f
        val multiplier = if (measure.isComplex()) complexMeasureStyleMultiplier else 1f

        drawMeasureHeader(headerText, textMeasurer, headerTextStyle, drawColor)
        drawStrings(stringCount, yOffset, drawColor)

        // Hoist density-scaled constants
        val p1 = 1 * d * multiplier
        val p2 = 1 * d * multiplier
        val p6 = 3 * d * multiplier
        val p8 = 4 * d * multiplier
        val p10 = 5 * d * multiplier
        val p24 = 12 * d * multiplier
        val p26 = 13 * d * multiplier
        val p32 = 16 * d * multiplier
        val p40 = 20 * d * multiplier
        val p60 = 30 * d * multiplier
        val p80 = 40 * d * multiplier

        if (isRepeatOpen) {
            drawLine(drawColor, Offset(0f, yOffset - p1), Offset(0f, stringCount * yOffset + p1), strokeWidth = p8)
            drawLine(drawColor, Offset(p10, yOffset), Offset(p10, stringCount * yOffset), strokeWidth = p2)
            drawCircle(drawColor, p6, Offset(p24, ((stringCount + 1f) * yOffset / 2f) - p26))
            drawCircle(drawColor, p6, Offset(p24, ((stringCount + 1f) * yOffset / 2f) + p26))
        } else {
            drawLine(drawColor, Offset(0f, yOffset), Offset(0f, stringCount * yOffset), strokeWidth = p2)
        }

        if (isLastMeasure || repeatClose > 0) {
            drawLine(drawColor, Offset(size.width - p10, yOffset), Offset(size.width - p10, stringCount * yOffset), strokeWidth = p2)
            drawLine(drawColor, Offset(size.width, yOffset - p1), Offset(size.width, stringCount * yOffset + p2), strokeWidth = p8)

            if (repeatClose > 0) {
                drawCircle(drawColor, p6, Offset(size.width - p24, ((stringCount + 1f) * yOffset / 2f) - p26))
                drawCircle(drawColor, p6, Offset(size.width - p24, ((stringCount + 1f) * yOffset / 2f) + p26))
                drawText(textMeasurer = textMeasurer, text = "${repeatClose}x", topLeft = Offset(size.width - p32, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
            }
        } else {
            drawLine(drawColor, Offset(size.width, yOffset), Offset(size.width, stringCount * yOffset), strokeWidth = p2)
        }

        if (!repeatAlternatives.isNullOrEmpty()) {
            drawText(textMeasurer = textMeasurer, text = repeatAlternatives, topLeft = Offset(size.width / 2f - p32, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
        }

        timeSignature?.let {
            drawText(textMeasurer, "${it.numerator}", Offset(yOffset, ((stringCount + .5f) * yOffset / 2f) - timeSignatureTextStyle.lineHeight.value), timeSignatureTextStyle.copy(color = drawColor))
            drawText(textMeasurer, "${it.denominator.value}", Offset(yOffset, (stringCount + .5f) * yOffset / 2f), timeSignatureTextStyle.copy(color = drawColor))
        }

        val beatAreaWidth = size.width - p80
        val beats = voice?.beats
        
        if (beats != null) {
            var currentBeatOffset = p40
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
                        yOffset = stringCount * yOffset + p60,
                        currentBeatOffset = currentBeatOffset,
                        headerTextMeasurer = textMeasurer,
                        headerTextStyle = headerTextStyle,
                        drawColor = drawColor,
                        layoutResult = beatLayouts.getOrNull(index)
                    )
                }

                currentBeatOffset += beatAreaWidth / beat.duration.value
            }
        }

        val beamGroups = voice?.beamGroups
        if (beamGroups != null) {
            var currentBeamOffset = p40
            val verticalLineOffset = stringCount * yOffset + (beatTextStyle.lineHeight.value) * multiplier
            
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
