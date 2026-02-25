package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel

@Composable
fun Lyrics(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val lyrics by viewModel.lyrics.collectAsStateWithLifecycle()

    lyrics?.let {
        Text(
            text = it.lines.joinToString("\n") { it.text },
            modifier = modifier,
        )
    }
}
