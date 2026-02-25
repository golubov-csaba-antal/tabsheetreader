package com.zappyware.tabsheetreader.core.reader

import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.Song
import com.zappyware.tabsheetreader.core.data.SongInfo
import java.io.InputStream

interface IFileReader {

    suspend fun readSong(inputStream: InputStream): Song

}
