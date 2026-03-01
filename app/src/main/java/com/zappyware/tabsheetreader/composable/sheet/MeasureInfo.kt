package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MeasureInfo(
    tempo: Int?,
    tuning: String?
) {
    Row(
        modifier = Modifier.defaultMinSize(minHeight = 60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tempo?.let {
            Text(
                text = "\u2669",
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = " = $it"
            )
        }
        tuning?.let {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "Tuning: $it"
            )
        }
    }
}