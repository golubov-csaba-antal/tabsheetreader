package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.composable.common.iconResource
import com.zappyware.tabsheetreader.core.data.Instruments

@Composable
fun TrackSelectionMenu(
    viewModel: MainViewModel,
    isTrackSelectionMenuExpanded: Boolean,
    onDismissRequest: () -> Unit = {},
    selectedTrackIndex: Int,
    onSelectedTrackIndexChanged: (Int) -> Unit = {},
) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    val selectedBackgroundColor = if (isSystemInDarkTheme()) {
        Color.DarkGray
    } else {
        Color.LightGray
    }

    val color = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    val colorFilter = ColorFilter.tint(color)

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
            val instrument = Instruments.fromId(track.rse.instrument.instrument)
            DropdownMenuItem(
                modifier = Modifier.background(
                    if (selectedTrackIndex == index) {
                        selectedBackgroundColor
                    } else {
                        Color.Transparent
                    }
                ),
                leadingIcon = {
                    if (null != instrument) {
                        Image(
                            painter = painterResource(track.iconResource),
                            contentDescription = instrument.instrumentName,
                            colorFilter = colorFilter,
                        )
                    }
                },
                text = {
                    if (null == instrument) {
                        Text(
                            text = track.name,
                            color = color,
                        )
                    } else {
                        Column {
                            Text(
                                text = instrument.instrumentName,
                                color = color,
                            )
                            Text(
                                text = track.name,
                                color = color,
                            )
                        }
                    }
                },
                onClick = {
                    onSelectedTrackIndexChanged(index)
                }
            )
        }
    }
}
