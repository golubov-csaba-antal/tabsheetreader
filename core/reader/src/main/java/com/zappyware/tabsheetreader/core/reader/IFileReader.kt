package com.zappyware.tabsheetreader.core.reader

import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.SongInfo
import java.io.InputStream

interface IFileReader {

    var fileVersion: FileVersion?

    var songInfo: SongInfo?

    suspend fun openFile(inputStream: InputStream)

}
