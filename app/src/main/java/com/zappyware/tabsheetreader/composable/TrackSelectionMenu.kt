package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel

@Composable
fun TrackSelectionMenu(
    viewModel: MainViewModel,
    isTrackSelectionMenuExpanded: Boolean,
    onDismissRequest: () -> Unit = {},
    selectedTrackIndex: Int,
    onSelectedTrackIndexChanged: (Int) -> Unit = {},
) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    DropdownMenu(
        expanded = isTrackSelectionMenuExpanded,
        onDismissRequest = { onDismissRequest() },
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 16.dp
        ),
    ) {
        tracks.forEachIndexed { index, track ->
            DropdownMenuItem(
                modifier = Modifier.background(
                    if (selectedTrackIndex == index) {
                        Color.LightGray
                    } else {
                        Color.Transparent
                    }
                ),
                text = {
                    Text(
                        text = track.name
                    )
                },
                onClick = {
                    onSelectedTrackIndexChanged(index)
                }
            )
        }
    }
}
