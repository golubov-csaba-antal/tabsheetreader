package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.R
import com.zappyware.tabsheetreader.composable.sheet.FileVersion
import com.zappyware.tabsheetreader.composable.sheet.SongInfo

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val resources = LocalResources.current

    val fileVersion by viewModel.fileVersion.collectAsStateWithLifecycle()
    val songInfo by viewModel.songInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.openFile(resources.openRawResource(R.raw.schwarzes))
    }

    Column(
        modifier = modifier
    ) {
        fileVersion?.let { fileVersion ->
            FileVersion(
                fileVersion = fileVersion,
                modifier = Modifier,
            )
        }

        songInfo?.let { songInfo ->
            SongInfo(
                songInfo = songInfo,
                modifier = Modifier,
            )
        }
    }
}
