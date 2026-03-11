package com.zappyware.tabsheetreader.composable.sheet.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature

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
    repeatClose: Int,
    textMeasurer: TextMeasurer,
    repeatCloseTextStyle: TextStyle,
    drawColor: Color
) {
    val text = "${repeatClose}x"
    val textStyle = repeatCloseTextStyle.copy(
        color = drawColor,
        textAlign = TextAlign.Right
    )
    drawText(
        textMeasurer = textMeasurer,
        text = text,
        topLeft = Offset(size.width - textMeasurer.measure(text, textStyle).size.width, 0f),
        style = textStyle
    )
}

fun DrawScope.drawTimeSignature(
    timeSignature: TimeSignature?,
    textMeasurer: TextMeasurer,
    timeSignatureTextStyle: TextStyle,
    horizontalSpace: Float,
    stringCount: Int,
    stringDistance: Float,
    drawColor: Color,
) {
    timeSignature?.let {
        drawText(
            textMeasurer = textMeasurer,
            text = "${it.numerator}",
            topLeft = Offset(
                x = horizontalSpace,
                y = (stringCount * stringDistance / 2f) - stringDistance
            ),
            style = timeSignatureTextStyle.copy(
                color = drawColor
            )
        )
        drawText(
            textMeasurer = textMeasurer,
            text = "${it.denominator.value}",
            topLeft = Offset(
                x = horizontalSpace,
                y = (stringCount * stringDistance + stringDistance) / 2f
            ),
            style = timeSignatureTextStyle.copy(
                color = drawColor
            )
        )
    }
}

fun DrawScope.drawPalmMutes(
    yOffset: Float,
    currentBeatOffset: Float,
    headerTextMeasurer: TextMeasurer,
    headerTextStyle: TextStyle,
    color: Color,
    drawnPalmMute: Boolean,
    layoutResult: TextLayoutResult?,
) {
    drawText(
        textMeasurer = headerTextMeasurer,
        text = if (drawnPalmMute) "\u2014" else "PM",
        topLeft = Offset(
            x = currentBeatOffset - (layoutResult?.size?.width ?: 0) / 2f,
            y = yOffset
        ),
        style = headerTextStyle.copy(
            color = color,
        ),
    )
}
