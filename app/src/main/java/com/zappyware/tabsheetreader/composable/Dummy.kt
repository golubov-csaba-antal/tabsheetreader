package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.R

@Composable
fun Dummy(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier,
) {
    val resources = LocalResources.current

    val fileVersion by viewModel.fileVersion.collectAsStateWithLifecycle()
    val songInfo by viewModel.songInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.openFile(resources.openRawResource(R.raw.tab))
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
        fileVersion?.let { fileVersion ->
            Text(
                modifier = modifier,
                text = "File version:",
            )
            Text(
                text = fileVersion.version,
            )
        }

        songInfo?.let { songInfo ->
            Text(
                modifier = modifier,
                text = "Song info:",
            )
            Text(
                text = "Title: ${songInfo.title}",
            )
            Text(
                text = "Sub-title: ${songInfo.subTitle}",
            )
            Text(
                text = "Artist: ${songInfo.artist}",
            )
            Text(
                text = "Album: ${songInfo.album}",
            )
            Text(
                text = "Words: ${songInfo.words}",
            )
            Text(
                text = "Music: ${songInfo.music}",
            )
            Text(
                text = "Copyright: ${songInfo.copyright}",
            )
            Text(
                text = "Tab: ${songInfo.tab}",
            )
            Text(
                text = "Instructions: ${songInfo.instructions}",
            )

            songInfo.notices.takeIf { it.isNotEmpty() }?.let { notices ->
                Text(
                    text = "Notices",
                )
                Text(
                    text = notices.joinToString(", "),
                )
            }
        }
    }
}
