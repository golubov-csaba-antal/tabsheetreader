package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText

fun DrawScope.drawVerticalLine(
    drawColor: Color,
    startingOffset: Float,
    stringCount: Int,
    stringDistance: Float,
    lineWidth: Float,
) {
    drawLine(
        color = drawColor,
        start = Offset(
            x = startingOffset,
            y = stringDistance
        ),
        end = Offset(
            x = startingOffset,
            y = stringCount * stringDistance
        ),
        strokeWidth = lineWidth
    )
}

fun DrawScope.drawRepeatOpen(
    drawColor: Color,
    stringCount: Int,
    stringDistance: Float,
    thickLineWidth: Float,
    thinLineWidth: Float,
    circleRadius: Float
) {
    // draw the thick vertical line
    drawLine(
        color = drawColor,
        start = Offset(
            x = 0f,
            y = stringDistance),
        end = Offset(
            x = 0f,
            y = stringCount * stringDistance
        ),
        strokeWidth = thickLineWidth
    )
    // draw the thin vertical line
    drawLine(
        color = drawColor,
        start = Offset(
            x = thickLineWidth * 1.25f,
            y = stringDistance),
        end = Offset(
            x = thickLineWidth * 1.25f,
            y = stringCount * stringDistance
        ),
        strokeWidth = thinLineWidth
    )
    //draw the circles
    drawCircle(
        color = drawColor,
        radius = circleRadius,
        center = Offset(
            x = thickLineWidth * 3f,
            y = ((stringCount + 1f) * stringDistance / 2f) - stringDistance
        )
    )
    drawCircle(
        color = drawColor,
        radius = circleRadius,
        center = Offset(
            x = thickLineWidth * 3f,
            y = ((stringCount + 1f) * stringDistance / 2f) + stringDistance
        )
    )
}

fun DrawScope.drawSongClosure(
    drawColor: Color,
    stringCount: Int,
    stringDistance: Float,
    thickLineWidth: Float,
    thinLineWidth: Float,
) {
    drawLine(
        color = drawColor,
        start = Offset(
            x = size.width - (thickLineWidth * 1.25f),
            y = stringDistance
        ),
        end = Offset(
            x = size.width - (thickLineWidth * 1.25f),
            y = stringCount * stringDistance
        ),
        strokeWidth = thinLineWidth
    )
    drawLine(
        color = drawColor,
        start = Offset(
            x = size.width,
            y = stringDistance
        ),
        end = Offset(
            x = size.width,
            y = stringCount * stringDistance + thinLineWidth
        ),
        strokeWidth = thickLineWidth
    )
}

fun DrawScope.drawRepeat(
    drawColor: Color,
    stringCount: Int,
    stringDistance: Float,
    thickLineWidth: Float,
    thinLineWidth: Float,
    circleRadius: Float
) {
    drawSongClosure(
        stringCount = stringCount,
        stringDistance = stringDistance,
        drawColor = drawColor,
        thickLineWidth = thickLineWidth,
        thinLineWidth = thinLineWidth,
    )
    //draw the circles
    drawCircle(
        color = drawColor,
        radius = circleRadius,
        center = Offset(
            x = size.width - (thickLineWidth * 3f),
            y = ((stringCount + 1f) * stringDistance / 2f) - stringDistance
        )
    )
    drawCircle(
        color = drawColor,
        radius = circleRadius,
        center = Offset(
            x = size.width - (thickLineWidth * 3f),
            y = ((stringCount + 1f) * stringDistance / 2f) + stringDistance
        )
    )
}

fun DrawScope.drawRepeatTimes(
    layoutResult: TextLayoutResult,
    drawColor: Color
) {
    drawText(
        textLayoutResult = layoutResult,
        topLeft = Offset(size.width - layoutResult.size.width, 0f),
        color = drawColor
    )
}

fun DrawScope.drawTimeSignature(
    numeratorLayout: TextLayoutResult,
    denominatorLayout: TextLayoutResult,
    horizontalSpace: Float,
    stringCount: Int,
    stringDistance: Float,
    drawColor: Color,
) {
    drawText(
        textLayoutResult = numeratorLayout,
        topLeft = Offset(
            x = horizontalSpace,
            y = (stringCount * stringDistance / 2f) - stringDistance
        ),
        color = drawColor
    )
    drawText(
        textLayoutResult = denominatorLayout,
        topLeft = Offset(
            x = horizontalSpace,
            y = (stringCount * stringDistance + stringDistance) / 2f
        ),
        color = drawColor
    )
}

fun DrawScope.drawPalmMutes(
    yOffset: Float,
    currentBeatOffset: Float,
    color: Color,
    pmLayoutResult: TextLayoutResult,
    noteLayoutResult: TextLayoutResult?,
) {
    drawText(
        textLayoutResult = pmLayoutResult,
        topLeft = Offset(
            x = currentBeatOffset - (noteLayoutResult?.size?.width ?: 0) / 2f,
            y = yOffset
        ),
        color = color,
    )
}
