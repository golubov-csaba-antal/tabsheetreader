package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.zappyware.tabsheetreader.composable.sheet.draw.drawMeasureHeader
import com.zappyware.tabsheetreader.composable.sheet.draw.drawStrings
import com.zappyware.tabsheetreader.core.data.TimeSignature

@Composable
fun Measure(
    measureIndex: Int,
    measureTitle: String?,
    timeSignature: TimeSignature?,
    stringCount: Int,
    isFirstMeasure: Boolean,
    isLastMeasure: Boolean,
    isRepeatOpen: Boolean,
    repeatClose: Int,
    repeatAlternatives: String?,
    modifier: Modifier,
    typography: Typography,
) {
    val headerText = if(measureTitle.isNullOrEmpty()) {
        "$measureIndex"
    } else {
        "$measureIndex - $measureTitle"
    }

    val headerTextMeasurer = rememberTextMeasurer()
    val headerTextStyle = typography.headlineSmall

    val timeSignatureMeasurer = rememberTextMeasurer()
    val timeSignatureTextStyle = typography.displayMedium

    val repeatCloseMeasurer = rememberTextMeasurer()
    val repeatCloseTextStyle = typography.labelSmall

    val drawColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Canvas(
        modifier = modifier,
    ) {
        val yOffset = size.height / 10

        // draw the measure count and title e.g. "1 - Intro" or simply "2"
        drawMeasureHeader(headerText, headerTextMeasurer, headerTextStyle, drawColor)

        // draw the string lines
        drawStrings(stringCount, yOffset, drawColor)

        if (isRepeatOpen) {
            // draw the repeat border
            drawLine(drawColor, Offset(0f, yOffset - 1.dp.value), Offset(0f, stringCount * yOffset + 1.dp.value), strokeWidth = 8f)
            // draw the starting border
            drawLine(drawColor, Offset(10.dp.value, yOffset), Offset(10.dp.value, stringCount * yOffset), strokeWidth = 2f)
            // draw the repeat circles
            drawCircle(drawColor, 6f, Offset(24.dp.value, ((stringCount + 1f) * yOffset / 2f) - 26.dp.value))
            drawCircle(drawColor, 6f, Offset(24.dp.value, ((stringCount + 1f) * yOffset / 2f) + 26.dp.value))
        } else {
            // draw the starting border
            drawLine(drawColor, Offset(0f, yOffset), Offset(0f, stringCount * yOffset), strokeWidth = 2f)
        }

        if (isLastMeasure || repeatClose > 0) {
            // draw the ending border
            drawLine(drawColor, Offset(size.width - 10.dp.value, yOffset), Offset(size.width - 10.dp.value, stringCount * yOffset), strokeWidth = 2f)
            // draw the closure border
            drawLine(drawColor, Offset(size.width, yOffset - 1.dp.value), Offset(size.width, stringCount * yOffset + 2.dp.value), strokeWidth = 8f)

            if (repeatClose > 0) {
                // draw the repeat circles
                drawCircle(drawColor, 6f, Offset(size.width - 24.dp.value, ((stringCount + 1f) * yOffset / 2f) - 26.dp.value))
                drawCircle(drawColor, 6f, Offset(size.width - 24.dp.value, ((stringCount + 1f) * yOffset / 2f) + 26.dp.value))
                drawText(textMeasurer = repeatCloseMeasurer, text = "${repeatClose}x", topLeft = Offset(size.width - 32.dp.value, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
            }
        } else {
            // draw the ending border
            drawLine(drawColor, Offset(size.width, yOffset), Offset(size.width, stringCount * yOffset), strokeWidth = 2f)
        }

        if (!repeatAlternatives.isNullOrEmpty()) {
            drawText(textMeasurer = repeatCloseMeasurer, text = repeatAlternatives, topLeft = Offset(size.width / 2f - 32.dp.value, 0f), style = repeatCloseTextStyle.copy(color = drawColor))
        }

        // for first measure, we draw the time signature too (eg. 4/4) center vertically
        timeSignature?.let {
            drawText(
                textMeasurer = timeSignatureMeasurer,
                text = "${it.numerator}",
                topLeft = Offset(yOffset, ((stringCount + .5f) * yOffset / 2f) - timeSignatureTextStyle.lineHeight.value),
                style = timeSignatureTextStyle.copy(color = drawColor)
            )
            drawText(
                textMeasurer = timeSignatureMeasurer,
                text = "${it.denominator.value}",
                topLeft = Offset(yOffset, (stringCount + .5f) * yOffset / 2f),
                style = timeSignatureTextStyle.copy(color = drawColor)
            )
        }
    }
}
