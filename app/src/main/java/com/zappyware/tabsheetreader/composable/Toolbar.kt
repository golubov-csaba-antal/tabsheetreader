@file:OptIn(ExperimentalMaterial3Api::class)

package com.zappyware.tabsheetreader.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Toolbar(
    title: String,
    actions: @Composable (RowScope.() -> Unit) = { },
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior { true },
        actions = actions,
    )
}
