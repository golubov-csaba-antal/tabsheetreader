package com.zappyware.tabsheetreader.core.data

data class Song(
    val fileVersion: FileVersion,
    val songInfo: SongInfo,
    val tracks: List<Track>,
)
