package com.zappyware.tabsheetreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.zappyware.tabsheetreader.composable.TrackScreen
import com.zappyware.tabsheetreader.composable.TrackSelectionMenu
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
                val backStack = rememberSaveable { mutableStateListOf<Any>(Info) }

                var selectedMenuIndex by rememberSaveable { mutableIntStateOf(0) }

                var isTrackSelectionMenuExpanded by remember { mutableStateOf(false) }

                var selectedTrackIndex by rememberSaveable { mutableIntStateOf(0) }

                val mainViewModel: MainViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Toolbar(
                            title = "Tab Sheet Reader",
                        )
                    },
                    bottomBar = {
                        BottomMenuBar(
                            viewModel = mainViewModel,
                            selectedMenuIndex = selectedMenuIndex,
                            onSelectedMenuIndexChanged = { navKey, index ->
                                if(selectedMenuIndex == index) {
                                    if (selectedMenuIndex == 1) {
                                        isTrackSelectionMenuExpanded = true
                                    }
                                } else {
                                    selectedMenuIndex = index
                                    backStack.clear()
                                    backStack.add(navKey)
                                }
                            },
                            selectedTrackIndex = selectedTrackIndex,
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
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                                )
                            }
                            entry<Lyrics> {
                                Lyrics(
                                    viewModel = mainViewModel,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                                )
                            }
                            entry<Track> {
                                TrackScreen(
                                    viewModel = mainViewModel,
                                    selectedTrackIndex = selectedTrackIndex
                                )
                            }
                        },
                    )

                    TrackSelectionMenu(
                        viewModel = mainViewModel,
                        isTrackSelectionMenuExpanded = isTrackSelectionMenuExpanded,
                        onDismissRequest = { isTrackSelectionMenuExpanded = false },
                        selectedTrackIndex = selectedTrackIndex,
                        onSelectedTrackIndexChanged = {
                            selectedTrackIndex = it
                            isTrackSelectionMenuExpanded = false
                        }
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
