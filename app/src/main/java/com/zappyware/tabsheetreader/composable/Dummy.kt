package com.zappyware.tabsheetreader.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zappyware.tabsheetreader.MainViewModel
import com.zappyware.tabsheetreader.R

@Composable
fun Dummy(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier,
) {
    val resources = LocalResources.current

    LaunchedEffect(Unit) {
        viewModel.openFile(resources.openRawResource(R.raw.cuidado))
    }
    
    Text(
        modifier = modifier,
        text = "Dummy shit",
    )
}
