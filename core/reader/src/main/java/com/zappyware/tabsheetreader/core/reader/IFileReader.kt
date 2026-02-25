package com.zappyware.tabsheetreader.core.reader

import java.io.InputStream

interface IFileReader {

    fun openFile(inputStream: InputStream)

}
