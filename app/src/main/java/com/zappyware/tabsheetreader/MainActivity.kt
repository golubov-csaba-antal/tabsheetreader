package com.zappyware.tabsheetreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.zappyware.tabsheetreader.composable.MainScreen
import com.zappyware.tabsheetreader.composable.Toolbar
import com.zappyware.tabsheetreader.composable.TrackBar
import com.zappyware.tabsheetreader.composable.TrackScreen
import com.zappyware.tabsheetreader.composable.sheet.Lyrics
import com.zappyware.tabsheetreader.navigation.Info
import com.zappyware.tabsheetreader.navigation.Lyrics
import com.zappyware.tabsheetreader.navigation.Track
import com.zappyware.tabsheetreader.ui.theme.TabSheetReaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TabSheetReaderTheme {
                val backStack = remember { mutableStateListOf<Any>(Info) }

                val mainViewModel: MainViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Toolbar(
                            title = "Tab Sheet Reader",
                            actions = {
                                IconButton(
                                    onClick =  { backStack.add(Info) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = null,
                                        modifier = Modifier
                                    )
                                }

                                IconButton(
                                    onClick =  { backStack.add(Lyrics) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Menu,
                                        contentDescription = null,
                                        modifier = Modifier
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        TrackBar(
                            viewModel = mainViewModel,
                            backStack = backStack
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(innerPadding),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                        ),
                        entryProvider = entryProvider {
                            entry<Info> {
                                MainScreen(
                                    viewModel = mainViewModel,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            entry<Lyrics> {
                                Lyrics(
                                    viewModel = mainViewModel,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            entry<Track> {
                                TrackScreen(
                                    viewModel = mainViewModel,
                                    trackId = it.trackId,
                                    trackType = it.trackType,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TabSheetReaderTheme {
        MainScreen(
            viewModel = hiltViewModel(),
            modifier = Modifier.padding(16.dp)
        )
    }
}
