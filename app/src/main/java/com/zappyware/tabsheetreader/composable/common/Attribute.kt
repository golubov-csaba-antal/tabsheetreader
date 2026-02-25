package com.zappyware.tabsheetreader.composable.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Attribute(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    if(text.isNotEmpty()) {
        if(title.isNotEmpty()) {
            Text(
                modifier = modifier,
                text = "$title: $text",
            )
        } else {
            Text(
                modifier = modifier,
                text = text,
            )
        }
    }
}
