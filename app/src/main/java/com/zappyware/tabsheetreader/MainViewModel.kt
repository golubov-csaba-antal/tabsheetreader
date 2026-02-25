package com.zappyware.tabsheetreader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.SongInfo
import com.zappyware.tabsheetreader.core.data.Track
import com.zappyware.tabsheetreader.core.reader.IFileReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fileReader: IFileReader,
): ViewModel() {

    private val _fileVersion = MutableStateFlow<FileVersion?>(null)
    val fileVersion: StateFlow<FileVersion?> = _fileVersion.asStateFlow()

    private val _songInfo = MutableStateFlow<SongInfo?>(null)
    val songInfo: StateFlow<SongInfo?> = _songInfo.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    fun openFile(inputStream: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {
            inputStream.use {
                val song = fileReader.readSong(it)
                _fileVersion.emit(song.fileVersion)
                _songInfo.emit(song.songInfo)
                _tracks.emit(song.tracks)
            }
        }
    }
}
