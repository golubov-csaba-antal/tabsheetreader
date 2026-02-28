package com.zappyware.tabsheetreader.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.R
import com.zappyware.tabsheetreader.core.data.TrackType
import com.zappyware.tabsheetreader.navigation.Track

@Composable
fun TrackBar(
    viewModel: MainViewModel,
    backStack: SnapshotStateList<Any>,
) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
    ) {
        tracks.forEachIndexed { index, track ->
            val navIndex = index + 1
            val route = Track(track.number, track.name)

            NavigationBarItem(
                selected = selectedIndex == navIndex,
                onClick = {
                    backStack.clear()
                    backStack.add(route)
                    selectedIndex = navIndex
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_tracktype_guitar_electric),
                        contentDescription = track.name,
                    )
                },
                label = {
                    Text(text = track.name)
                }
            )
        }
    }
}

val TrackType.iconRes: Int get() =
    when(this) {
        TrackType.VOCAL -> return R.drawable.ic_tracktype_vocal
        TrackType.ELECTRIC_GUITAR -> return R.drawable.ic_tracktype_guitar_electric
        TrackType.ACOUSTIC_GUITAR -> return R.drawable.ic_tracktype_guitar_acoustic
        TrackType.BASS -> return R.drawable.ic_tracktype_bass
        TrackType.DRUM -> return R.drawable.ic_tracktype_drum
        TrackType.SYNTH -> return R.drawable.ic_tracktype_synth
    }

val TrackType.label: String get() =
    when(this) {
        TrackType.VOCAL -> return "Vocal"
        TrackType.ELECTRIC_GUITAR -> return "Guitar"
        TrackType.ACOUSTIC_GUITAR -> return "Guitar"
        TrackType.BASS -> return "Bass"
        TrackType.DRUM -> return "Drum"
        TrackType.SYNTH -> return "Synth"
    }