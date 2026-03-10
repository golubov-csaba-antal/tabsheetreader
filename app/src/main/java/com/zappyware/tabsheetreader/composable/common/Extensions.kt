package com.zappyware.tabsheetreader.composable.common

import androidx.compose.ui.text.TextStyle
import com.zappyware.tabsheetreader.core.data.song.measure.Measure

const val complexMeasureStyleMultiplier = 1.5f

fun Measure.isComplex() = voices.any { it.beats.size > 5 }

fun TextStyle.changeFontSizeForMeasure(measure: Measure) =
    if (measure.isComplex()) {
        copy(
            fontSize = fontSize * complexMeasureStyleMultiplier,
            lineHeight = lineHeight * complexMeasureStyleMultiplier
        )
    } else {
        this
    }
