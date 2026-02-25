package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zappyware.tabsheetreader.composable.common.Attribute
import com.zappyware.tabsheetreader.core.data.SongInfo

@Composable
fun SongInfo(
    songInfo: SongInfo,
    modifier: Modifier = Modifier,
) {
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
            text = songInfo.title,
        )
        Attribute(
            title = "Artist",
            text = songInfo.artist,
        )
        Attribute(
            title = "Sub-title",
            text = songInfo.subTitle,
        )
        Attribute(
            title = "Album",
            text = songInfo.album,
        )
        Attribute(
            title = "Words",
            text = songInfo.words,
        )
        Attribute(
            title = "Music",
            text = songInfo.music,
        )
        Attribute(
            title = "Copyright",
            text = songInfo.copyright,
        )
        Attribute(
            title = "Transcriber",
            text = songInfo.tab,
        )

        songInfo.notices.takeIf { it.isNotEmpty() }?.let { notices ->
            Text(
                text = "Notices",
            )
            Text(
                text = notices.joinToString(", "),
            )
        }

        Attribute(
            title = "Instructions",
            text = songInfo.instructions,
        )
    }
}
