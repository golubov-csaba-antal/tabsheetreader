package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.composable.sheet.Measure
import com.zappyware.tabsheetreader.ui.theme.MeasureTypography

@Composable
fun TrackScreen(
    viewModel: MainViewModel,
    selectedTrackIndex: Int,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
) {
    val headers by viewModel.measureHeaders.collectAsStateWithLifecycle()

    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    val selectedTrack = tracks.getOrNull(selectedTrackIndex)

    val displayMoreCells = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    if (headers.isEmpty()) {
        Text(
            modifier = modifier,
            text = "There were no headers in the file.",
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (displayMoreCells) 3 else 2),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(headers) {
                Measure(
                    measureIndex = it.number,
                    measureTitle = it.marker?.title,
                    timeSignature = it.timeSignature,
                    isFirstMeasure = it.number == 1,
                    isLastMeasure = it.number == headers.size,
                    stringCount = selectedTrack?.stringCount ?: DEFAULT_STRING_COUNT,
                    modifier = Modifier.aspectRatio(1.77f, false),
                    typography = MeasureTypography.getTypography(windowSizeClass)
                )
            }
        }
    }
}

private const val DEFAULT_STRING_COUNT = 6