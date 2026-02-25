package com.zappyware.tabsheetreader

import androidx.lifecycle.ViewModel
import com.zappyware.tabsheetreader.core.reader.IFileReader
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fileReader: IFileReader,
): ViewModel() {

    fun openFile(inputStream: InputStream) {
        fileReader.openFile(inputStream)
    }
}
