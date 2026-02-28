package com.zappyware.tabsheetreader

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.zappyware.tabsheetreader.composable.common.iconResource
import com.zappyware.tabsheetreader.navigation.Info
import com.zappyware.tabsheetreader.navigation.Lyrics
import com.zappyware.tabsheetreader.navigation.Track

@Composable
fun BottomMenuBar(
    viewModel: MainViewModel,
    selectedMenuIndex: Int,
    onSelectedMenuIndexChanged: (NavKey, Int) -> Unit,
    selectedTrackIndex: Int,
) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()
    val selectedTrack = tracks.getOrNull(selectedTrackIndex)

    val color = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    val colorFilter = ColorFilter.tint(color)

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
    ) {
        NavigationBarItem(
            selected = selectedMenuIndex == 1,
            onClick = {
                onSelectedMenuIndexChanged(Track, 1)
            },
            icon = {
                Image(
                    painter = painterResource(selectedTrack?.iconResource ?: R.drawable.unknown),
                    contentDescription = if (tracks.isEmpty()) {
                        "No tracks"
                    } else {
                        tracks[selectedTrackIndex].name
                    },
                    modifier = Modifier.size(24.dp),
                    colorFilter = colorFilter,
                )
            },
            label = {
                Text(
                    text = if (tracks.isEmpty()) {
                        "No tracks"
                    } else {
                        tracks[selectedTrackIndex].name
                    },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            },
        )
        NavigationBarItem(
            selected = selectedMenuIndex == 2,
            onClick = {
                onSelectedMenuIndexChanged(Info, 2)
            },
            icon = {
                Image(
                    painter = painterResource(R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = colorFilter,
                )
            },
            label = {
                Text(
                    text = "Song info",
                    color = color,
                )
            },
        )
        NavigationBarItem(
            selected = selectedMenuIndex == 3,
            onClick = {
                onSelectedMenuIndexChanged(Lyrics, 3)
            },
            icon = {
                Image(
                    painter = painterResource(R.drawable.lyrics),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = colorFilter,
                )
            },
            label = {
                Text(
                    text = "Lyrics",
                    color = color,
                )
            },
        )
    }
}
