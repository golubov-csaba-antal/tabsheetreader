package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zappyware.tabsheetreader.core.data.TimeSignature
import com.zappyware.tabsheetreader.ui.theme.MeasureTypography

@Composable
fun Measure(
    measureIndex: Int,
    measureTitle: String?,
    timeSignature: TimeSignature?,
    stringCount: Int,
    isFirstMeasure: Boolean,
    isLastMeasure: Boolean,
    modifier: Modifier,
    typography: Typography,
) {
    val headerTextMeasurer = rememberTextMeasurer()
    val headerTextStyle = typography.headlineSmall

    val timeSignatureMeasurer = rememberTextMeasurer()
    val timeSignatureTextStyle = typography.displayMedium

    Canvas(
        modifier = modifier,
    ) {
        val yOffset = size.height / 10

        // draw the measure count and title eg. "1 - Intro" or simply "2"
        drawText(
            textMeasurer = headerTextMeasurer,
            text = if(measureTitle.isNullOrEmpty()) {
                "$measureIndex"
            } else {
                "$measureIndex - $measureTitle"
            },
            topLeft = Offset(0f, 0f),
            style = headerTextStyle
        )

        // draw the string lines
        for (i in 1..stringCount) {
            drawLine(Color.Black, Offset(0f, i * yOffset), Offset(size.width, i * yOffset), strokeWidth = 2f)
        }

        // draw the starting border
        drawLine(Color.Black, Offset(0f, yOffset), Offset(0f, stringCount * yOffset), strokeWidth = 2f)

        if (isLastMeasure) {
            // draw the ending border
            drawLine(Color.Black, Offset(size.width - 10.dp.value, yOffset), Offset(size.width - 10.dp.value, stringCount * yOffset), strokeWidth = 2f)
            // draw the closure border
            drawLine(Color.Black, Offset(size.width, yOffset - 2.dp.value), Offset(size.width, stringCount * yOffset + 2.dp.value), strokeWidth = 8f)
        } else {
            // draw the ending border
            drawLine(Color.Black, Offset(size.width, yOffset), Offset(size.width, stringCount * yOffset), strokeWidth = 2f)
        }

        // for first measure, we draw the time signature too (eg. 4/4) center vertically
        if (isFirstMeasure) {
            drawText(
                textMeasurer = timeSignatureMeasurer,
                text = "${timeSignature?.numerator}",
                topLeft = Offset(yOffset, ((stringCount + .5f) * yOffset / 2f) - timeSignatureTextStyle.lineHeight.value),
                style = timeSignatureTextStyle
            )
            drawText(
                textMeasurer = timeSignatureMeasurer,
                text = "${timeSignature?.denominator?.value}",
                topLeft = Offset(yOffset, (stringCount + .5f) * yOffset / 2f),
                style = timeSignatureTextStyle
            )
        }
    }
}
