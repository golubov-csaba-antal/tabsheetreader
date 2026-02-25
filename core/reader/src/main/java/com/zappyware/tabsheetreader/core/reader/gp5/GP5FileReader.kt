package com.zappyware.tabsheetreader.core.reader.gp5

import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.Song
import com.zappyware.tabsheetreader.core.data.SongInfo
import com.zappyware.tabsheetreader.core.data.Track
import com.zappyware.tabsheetreader.core.data.TrackType
import com.zappyware.tabsheetreader.core.reader.IFileReader
import com.zappyware.tabsheetreader.core.reader.readByteSizeString
import com.zappyware.tabsheetreader.core.reader.readI32
import com.zappyware.tabsheetreader.core.reader.readIByteSizeString
import java.io.InputStream
import javax.inject.Inject

class GP5FileReader @Inject constructor(): IFileReader {

    override suspend fun readSong(inputStream: InputStream): Song =
        Song(
            fileVersion = readFileVersion(inputStream),
            songInfo = readSongInfo(inputStream),
            // Faking tracks to test the NavigationBar
            tracks = TrackType.entries.mapIndexed { index, type -> Track(index.toLong(), type.name, type) },
        )

    private fun readFileVersion(inputStream: InputStream): FileVersion =
        FileVersion(
            version = inputStream.readByteSizeString(30)
        )

    private fun readSongInfo(inputStream: InputStream): SongInfo =
        SongInfo(
            title = inputStream.readIByteSizeString(),
            subTitle = inputStream.readIByteSizeString(),
            artist = inputStream.readIByteSizeString(),
            album = inputStream.readIByteSizeString(),
            words = inputStream.readIByteSizeString(),
            music = inputStream.readIByteSizeString(),
            copyright = inputStream.readIByteSizeString(),
            tab = inputStream.readIByteSizeString(),
            instructions = inputStream.readIByteSizeString(),
            notices = run {
                val noticeCount = inputStream.readI32()
                List(noticeCount) {
                    inputStream.readIByteSizeString()
                }
            }
        )
}
