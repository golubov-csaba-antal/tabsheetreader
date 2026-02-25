package com.zappyware.tabsheetreader.core.data

data class SongInfo(
    val title: String,
    val subTitle: String,
    val artist: String,
    val album: String,
    val words: String,
    val music: String,
    val copyright: String,
    val tab: String,
    val instructions: String,
    val notices: List<String>,
)
