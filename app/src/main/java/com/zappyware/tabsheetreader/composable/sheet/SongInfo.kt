package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.composable.common.Attribute

@Composable
fun SongInfo(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val songInfo by viewModel.songInfo.collectAsStateWithLifecycle()

    songInfo?.let { info ->
        Column(
            modifier = modifier,
        ) {
            Text(
                modifier = modifier.padding(vertical = 8.dp),
                text = "Song info:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Attribute(
                title = "Title",
                text = info.title,
            )
            Attribute(
                title = "Artist",
                text = info.artist,
            )
            Attribute(
                title = "Sub-title",
                text = info.subTitle,
            )
            Attribute(
                title = "Album",
                text = info.album,
            )
            Attribute(
                title = "Words",
                text = info.words,
            )
            Attribute(
                title = "Music",
                text = info.music,
            )
            Attribute(
                title = "Copyright",
                text = info.copyright,
            )
            Attribute(
                title = "Transcriber",
                text = info.tab,
            )

            info.notices.takeIf { it.isNotEmpty() }?.let { notices ->
                Text(
                    text = "Notices",
                )
                Text(
                    text = notices.joinToString(", "),
                )
            }

            Attribute(
                title = "Instructions",
                text = info.instructions,
            )
        }
    } ?: run {
        Text(
            modifier = modifier,
            text = "No further information provided.",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
