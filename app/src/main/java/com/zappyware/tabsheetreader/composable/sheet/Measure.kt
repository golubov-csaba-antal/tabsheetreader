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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeat
import com.zappyware.tabsheetreader.composable.sheet.draw.drawBeatLine
import com.zappyware.tabsheetreader.composable.sheet.draw.drawMeasureHeader
import com.zappyware.tabsheetreader.composable.sheet.draw.drawStrings
import com.zappyware.tabsheetreader.core.data.song.measure.Measure
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.hasPalmMute
import com.zappyware.tabsheetreader.core.data.song.measure.beat.isRest
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

    val headerTextMeasurer = rememberTextMeasurer()
    val headerTextStyle = typography.headlineSmall

    val timeSignatureMeasurer = rememberTextMeasurer()
    val timeSignatureTextStyle = typography.displayMedium

    val repeatCloseMeasurer = rememberTextMeasurer()
    val repeatCloseTextStyle = typography.labelSmall

    val beatTextMeasurer = rememberTextMeasurer()
    val beatTextStyle = typography.bodySmall

    val backgroundColor = MaterialTheme.colorScheme.background
    val drawColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    val voice = measure.voices.firstOrNull()
    
    // Pre-calculate text layouts for beats to avoid measuring during Draw phase
    val beatLayouts = remember(voice, beatTextStyle, drawColor) {
        voice?.beats?.map { beat ->
            beat.notes.map { note ->
                val text = when (note.type) {
                    NoteType.Rest -> "\u23F9"
                    NoteType.Dead -> "X"
                    else -> note.value.toString()
                }
                beatTextMeasurer.measure(text, beatTextStyle.copy(color = drawColor))
            }
        } ?: emptyList()
    }

    Canvas(
        modifier = modifier,
    ) {
        val yOffset = size.height / 10

        drawMeasureHeader(headerText, headerTextMeasurer, headerTextStyle, drawColor)
        drawStrings(stringCount, yOffset, drawColor)

        if (isRepeatOpen) {
            drawLine(drawColor, Offset(0f, yOffset - 1.dp.value), Offset(0f, stringCount * yOffset + 1.dp.value), strokeWidth = 8f)
            drawLine(drawColor, Offset(10.dp.value, yOffset), Offset(10.dp.value, stringCount * yOffset), strokeWidth = 2f)
            drawCircle(drawColor, 6f, Offset(24.dp.value, ((stringCount + 1f) * yOffset / 2f) - 26.dp.value))
            drawCircle(drawColor, 6f, Offset(24.dp.value, ((stringCount + 1f) * yOffset / 2f) + 26.dp.value))
        } else {
            drawLine(drawColor, Offset(0f, yOffset), Offset(0f, stringCount * yOffset), strokeWidth = 2f)
        }

        if (isLastMeasure || repeatClose > 0) {
            drawLine(drawColor, Offset(size.width - 10.dp.value, yOffset), Offset(size.width - 10.dp.value, stringCount * yOffset), strokeWidth = 2f)
            drawLine(drawColor, Offset(size.width, yOffset - 1.dp.value), Offset(size.width, stringCount * yOffset + 2.dp.value), strokeWidth = 8f)

            if (repeatClose > 0) {
                drawCircle(drawColor, 6f, Offset(size.width - 24.dp.value, ((stringCount + 1f) * yOffset / 2f) - 26.dp.value))
                drawCircle(drawColor, 6f, Offset(size.width - 24.dp.value, ((stringCount + 1f) * yOffset / 2f) + 26.dp.value))
                drawText(textMeasurer = repeatCloseMeasurer, text = "${repeatClose}x", topLeft = Offset(size.width - 32.dp.value, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
            }
        } else {
            drawLine(drawColor, Offset(size.width, yOffset), Offset(size.width, stringCount * yOffset), strokeWidth = 2f)
        }

        if (!repeatAlternatives.isNullOrEmpty()) {
            drawText(textMeasurer = repeatCloseMeasurer, text = repeatAlternatives, topLeft = Offset(size.width / 2f - 32.dp.value, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
        }

        timeSignature?.let {
            drawText(timeSignatureMeasurer, "${it.numerator}", Offset(yOffset, ((stringCount + .5f) * yOffset / 2f) - timeSignatureTextStyle.lineHeight.value), timeSignatureTextStyle.copy(color = drawColor))
            drawText(timeSignatureMeasurer, "${it.denominator.value}", Offset(yOffset, (stringCount + .5f) * yOffset / 2f), timeSignatureTextStyle.copy(color = drawColor))
        }

        var beatOffset = if (isRepeatOpen) 40.dp.value else 16.dp.value
        val beatAreaWidth = size.width - beatOffset - (if (repeatClose > 0) 32.dp.value else 16.dp.value)

        voice?.beats?.let { beats ->
            val longestBeatDuration = beats.minOf { it.duration.value }
            beats.forEachIndexed { index, beat ->
                drawBeat(
                    beat = beat,
                    textMeasurer = beatTextMeasurer,
                    textStyle = beatTextStyle,
                    backgroundColor = backgroundColor,
                    color = drawColor,
                    beatOffset = beatOffset,
                    stringOffset = yOffset,
                    cachedLayouts = beatLayouts.getOrNull(index)
                )

                val currentBeatOffset = beatOffset
                beatOffset += beatAreaWidth / beat.duration.value

                if (!beat.isRest()) {
                    drawBeatLine(
                        beat = beat,
                        color = drawColor,
                        beatOffset = currentBeatOffset + ((beatLayouts.getOrNull(index)
                            ?.firstOrNull()?.size?.width ?: 0) / 2f),
                        nextBeatOffset = beatOffset + ((beatLayouts.getOrNull(index + 1)
                            ?.firstOrNull()?.size?.width?.toFloat() ?: 32.dp.value) / 2f),
                        verticalOffset = stringCount * yOffset + (beatTextStyle.lineHeight.value / 2f),
                        longestBeatDuration = longestBeatDuration,
                    )

                    // draw end vertical line
                    if (index == beats.lastIndex) {
                        val lineStart =
                            stringCount * yOffset + (beatTextStyle.lineHeight.value / 2f)
                        drawLine(
                            color = drawColor,
                            start = Offset(beatOffset + 16.dp.value, lineStart),
                            end = Offset(
                                beatOffset + 16.dp.value,
                                lineStart + 50.dp.value - longestBeatDuration
                            ),
                            strokeWidth = 2.dp.value,
                        )
                    }

                    if (beat.hasPalmMute()) {
                        drawText(
                            textMeasurer = headerTextMeasurer,
                            text = "P.M.",
                            topLeft = Offset(
                                currentBeatOffset - ((beatLayouts.getOrNull(index)
                                    ?.firstOrNull()?.size?.width ?: 0) / 2f),
                                stringCount * yOffset + 60.dp.value
                            ),
                            style = headerTextStyle.copy(
                                color = drawColor,
                                fontSize = (headerTextStyle.fontSize.value * 0.75).sp
                            ),
                        )
                    }
                }
            }
        }
    }
}
