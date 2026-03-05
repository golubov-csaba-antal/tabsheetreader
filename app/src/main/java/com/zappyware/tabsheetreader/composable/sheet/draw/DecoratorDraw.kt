package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun DrawScope.drawPalmMutes(
    stringCount: Int,
    yOffset: Float,
    currentBeatOffset: Float,
    headerTextMeasurer: TextMeasurer,
    headerTextStyle: TextStyle,
    drawColor: Color,
    layoutResult: List<TextLayoutResult>?,
) {
    drawText(
        textMeasurer = headerTextMeasurer,
        text = "P.M.",
        topLeft = Offset(
            currentBeatOffset - (layoutResult?.firstOrNull()?.size?.width ?: 0) / 2f,
            stringCount * yOffset + 60.dp.value
        ),
        style = headerTextStyle.copy(
            color = drawColor,
            fontSize = (headerTextStyle.fontSize.value * 0.75).sp
        ),
    )
}
