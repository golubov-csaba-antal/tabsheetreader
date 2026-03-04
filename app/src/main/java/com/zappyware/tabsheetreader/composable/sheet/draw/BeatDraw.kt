package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteType

fun DrawScope.drawBeat(
    beat: Beat,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    backgroundColor: Color,
    color: Color,
    beatOffset: Float,
    stringOffset: Float,
    cachedLayouts: List<TextLayoutResult>? = null,
) {
    if (beat.notes.isEmpty()) {
        drawRect(
            color = color,
            topLeft = Offset(
                3 * stringOffset,
                2.65f * stringOffset
            ),
            size = Size(stringOffset, 0.75f * stringOffset)
        )
    } else {
        val style = textStyle.copy(color = color, fontWeight = FontWeight.Bold)
        beat.notes.forEachIndexed { index, note ->
            val layoutResult = cachedLayouts?.getOrNull(index) ?: run {
                val text = when (note.type) {
                    NoteType.Rest -> "\u23F9"
                    NoteType.Dead -> "X"
                    else -> note.value.toString()
                }
                textMeasurer.measure(text, style)
            }

            val topLeft = Offset(
                beatOffset,
                note.string * stringOffset - (textStyle.lineHeight.value * 0.75f)
            )

            drawRect(
                color = backgroundColor,
                topLeft = topLeft,
                size = Size(layoutResult.size.width.toFloat(), layoutResult.size.height.toFloat())
            )

            drawText(
                textLayoutResult = layoutResult,
                topLeft = topLeft
            )
        }
    }
}

fun DrawScope.drawBeatLine(
    beat: Beat,
    color: Color,
    beatOffset: Float,
    nextBeatOffset: Float,
    verticalOffset: Float,
    longestBeatDuration: Int,
) {
    // draw the horizontal line which actually symbolizes the beat duration too
    var durationValue = Durations.EIGHTH.value
    while (durationValue <= beat.duration.value) {
        drawLine(
            color = color,
            start = Offset(beatOffset, verticalOffset + 48.dp.value - durationValue.dp.value),
            end = Offset(nextBeatOffset, verticalOffset + 48.dp.value - durationValue.dp.value),
            strokeWidth = 4.dp.value,
        )
        durationValue *= 2
    }

    // draw the vertical line
    drawLine(
        color = color,
        start = Offset(beatOffset - 1.dp.value, verticalOffset),
        end = Offset(beatOffset - 1.dp.value, verticalOffset + 50.dp.value - longestBeatDuration),
        strokeWidth = 2.dp.value,
    )
}
