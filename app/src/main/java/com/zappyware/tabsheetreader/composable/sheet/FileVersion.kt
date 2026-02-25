package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zappyware.tabsheetreader.composable.common.Attribute
import com.zappyware.tabsheetreader.core.data.FileVersion

@Composable
fun FileVersion(
    fileVersion: FileVersion,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = modifier.padding(vertical = 8.dp),
            text = "File:",
            style = MaterialTheme.typography.headlineSmall,
        )
        Attribute(
            title = "Version",
            text = fileVersion.version,
        )
    }
}
