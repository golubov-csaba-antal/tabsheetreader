package com.zappyware.tabsheetreader.composable.sheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass
import com.zappyware.tabsheetreader.MusicalCharacters
import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.ui.theme.MeasureTypography

@Composable
fun MeasureInfo(
    tempo: Int?,
    tuning: String?,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
) {
    Row(
        modifier = Modifier.defaultMinSize(minHeight = 60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tempo?.let {
            Text(
                text = MusicalCharacters.getNotationCharacter(Durations.QUARTER.value),
                style = MeasureTypography.getTypography(windowSizeClass).bodyMedium
            )
            Text(
                modifier = Modifier.padding(end = 16.dp, bottom = 24.dp),
                text = " = $it",
                style = MeasureTypography.getTypography(windowSizeClass).bodyLarge
            )
        }
        tuning?.let {
            Text(
                modifier = Modifier.padding(end = 16.dp, bottom = 24.dp),
                text = "Tuning: $it",
                style = MeasureTypography.getTypography(windowSizeClass).bodyLarge
            )
        }
    }
}