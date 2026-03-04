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
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteType

fun DrawScope.drawBeat(
    beat: Beat,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    backgroundColor: Color,
    color: Color,
    offset: Offset,
    cachedLayouts: List<TextLayoutResult>? = null,
) {
    if (beat.notes.isEmpty()) {
        drawRect(
            color = color,
            topLeft = Offset(
                3 * offset.x,
                2.65f * offset.y
            ),
            size = Size(offset.y, 0.75f * offset.y)
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
                (1.25f * offset.x),
                note.string * offset.y - (textStyle.lineHeight.value * 0.75f)
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
