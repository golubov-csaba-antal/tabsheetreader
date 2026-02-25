package com.zappyware.tabsheetreader.core.data

data class Page(
    val width: Int,
    val height: Int,
    val leftMargin: Int,
    val topMargin: Int,
    val rightMargin: Int,
    val bottomMargin: Int,
    val sizeProportion: Float,
    val header: Int,
    val title: String,
    val subTitle: String,
    val artist: String,
    val album: String,
    val words: String,
    val music: String,
    val wordsAndMusic: String,
    val copyright: String,
    val pageNumber: String,
)
