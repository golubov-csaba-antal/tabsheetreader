package com.zappyware.tabsheetreader.di

import com.zappyware.tabsheetreader.core.reader.IFileReader
import com.zappyware.tabsheetreader.core.reader.gp5.GP5FileReader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface HiltModule {

    @Binds
    fun bindFileReader(fileReader: GP5FileReader): IFileReader
}
