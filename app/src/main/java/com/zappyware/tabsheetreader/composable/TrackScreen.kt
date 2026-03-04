package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import com.zappyware.tabsheetreader.composable.sheet.MeasureInfo
import com.zappyware.tabsheetreader.core.data.song.track.tuning
import com.zappyware.tabsheetreader.ui.theme.MeasureTypography

@Composable
fun TrackScreen(
    viewModel: MainViewModel,
    selectedTrackIndex: Int,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
) {
    val measures by viewModel.measures.collectAsStateWithLifecycle()

    val tempo by viewModel.tempo.collectAsStateWithLifecycle()

    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    val selectedTrack = tracks.getOrNull(selectedTrackIndex)

    val tuning = selectedTrack?.takeIf { it.indicateTuning }?.strings?.reversed()?.joinToString("") { it.tuning }

    val displayMoreCells = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    val trackMeasures = measures.filter { it.track == selectedTrack }

    if (trackMeasures.isEmpty()) {
        Text(
            modifier = modifier,
            text = "There were no measures in the file.",
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (displayMoreCells) 3 else 2),
            contentPadding = PaddingValues(16.dp)
        ) {
            item(
                contentType = ITEM_TYPE_INFO,
                span = { GridItemSpan(maxLineSpan) }
            ) {
                MeasureInfo(
                    tempo?.takeIf { !it.hide }?.tempo,
                    tuning
                )
            }
            items(
                contentType = { ITEM_TYPE_MEASURE },
                items = trackMeasures
            ) { measure ->
                Measure(
                    measure = measure,
                    measureCount = trackMeasures.size,
                    selectedTrack = selectedTrack,
                    stringCount = selectedTrack?.stringCount ?: DEFAULT_STRING_COUNT,
                    modifier = Modifier.aspectRatio(1.77f, false),
                    typography = MeasureTypography.getTypography(windowSizeClass)
                )
            }
        }
    }
}

private const val DEFAULT_STRING_COUNT = 6

private object ITEM_TYPE_INFO
private object ITEM_TYPE_MEASURE