package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight

fun DrawScope.drawMeasureHeader(
    headerTitle: String,
    textMeasurer: TextMeasurer,
    textSyle: TextStyle,
    color: Color,
) {
    drawText(
        textMeasurer = textMeasurer,
        text = headerTitle,
        topLeft = Offset(0f, 0f),
        style = textSyle.copy(color = color, fontWeight = FontWeight.Bold)
    )
}

fun DrawScope.drawStrings(
    stringCount: Int,
    offset: Float,
    color: Color,
) {
    for (i in 1..stringCount) {
        drawLine(
            color = color,
            start = Offset(0f, i * offset),
            end = Offset(size.width, i * offset),
            strokeWidth = 2f
        )
    }
}