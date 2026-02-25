package com.zappyware.tabsheetreader.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.core.data.TrackType

@Composable
fun TrackScreen(
    viewModel: MainViewModel,
    trackId: Long,
    trackType: TrackType,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Placeholder for ${trackType.label} track.",
    )
}
