package com.zappyware.tabsheetreader.composable.common

import com.zappyware.tabsheetreader.core.data.song.measure.Measure

fun Measure.isComplex() = voices.any { it.beats.size > 5 }
