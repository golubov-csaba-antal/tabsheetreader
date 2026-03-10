package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.isRest

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
    if (cachedLayouts.isNullOrEmpty()) return

    // Pre-calculate the Y adjustment based on the line height
    // Hoisting this calculation out of the loop
    val yAdjustment = textStyle.lineHeight.toPx() / 2
    val notes = beat.notes

    if (notes.isEmpty()) {
        // Handle whole rest note using the first cached layout
        val layoutResult = cachedLayouts[0]
        val width = layoutResult.size.width.toFloat()
        val topLeft = Offset(
            beatOffset - (width / 2f),
            stringOffset - yAdjustment
        )

        drawText(
            textLayoutResult = layoutResult,
            topLeft = topLeft,
            color = color
        )
    } else {
        // Iterate through notes using a for-loop to avoid iterator allocation
        for (i in notes.indices) {
            val note = notes[i]
            val layoutResult = cachedLayouts.getOrNull(i) ?: continue

            val width = layoutResult.size.width.toFloat()
            val height = layoutResult.size.height.toFloat()

            val topLeft = Offset(
                beatOffset - (width / 2f),
                (if (beat.isRest()) 3 else note.string) * stringOffset - yAdjustment
            )

            // Draw background rectangle to clear string lines under the note
            drawRect(
                color = backgroundColor,
                topLeft = topLeft,
                size = Size(width, height)
            )

            drawText(
                textLayoutResult = layoutResult,
                topLeft = topLeft,
                color = color
            )
        }
    }
}
