package com.zappyware.tabsheetreader

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
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

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
    ) {
        NavigationBarItem(
            selected = selectedMenuIndex == 1,
            onClick = {
                onSelectedMenuIndexChanged(Track, 1)
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_tracktype_guitar_electric),
                    contentDescription = null,
                    modifier = Modifier
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
                    maxLines = 1
                )
            },
        )
        NavigationBarItem(
            selected = selectedMenuIndex == 2,
            onClick = {
                onSelectedMenuIndexChanged(Info, 2)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    modifier = Modifier
                )
            },
            label = {
                Text(text = "Song info")
            },
        )
        NavigationBarItem(
            selected = selectedMenuIndex == 3,
            onClick = {
                onSelectedMenuIndexChanged(Lyrics, 3)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null,
                    modifier = Modifier
                )
            },
            label = {
                Text(text = "Lyrics")
            },
        )
    }
}
