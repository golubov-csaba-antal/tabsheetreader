package com.zappyware.tabsheetreader.core.reader.gp5

import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.SongInfo
import com.zappyware.tabsheetreader.core.reader.IFileReader
import com.zappyware.tabsheetreader.core.reader.readByteSizeString
import com.zappyware.tabsheetreader.core.reader.readI32
import com.zappyware.tabsheetreader.core.reader.readIByteSizeString
import java.io.InputStream
import javax.inject.Inject
import kotlin.String

class GP5FileReader @Inject constructor(): IFileReader {

    override var fileVersion: FileVersion? = null

    override var songInfo: SongInfo? = null

    override suspend fun openFile(inputStream: InputStream) {
        fileVersion = FileVersion(
            version = inputStream.readByteSizeString(30)
        )
        songInfo = SongInfo(
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
}
