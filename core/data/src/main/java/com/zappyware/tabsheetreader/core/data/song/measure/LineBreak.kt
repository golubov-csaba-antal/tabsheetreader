package com.zappyware.tabsheetreader.core.data.song.measure

enum class LineBreak {
    None,
    Break,
    Protect,
}

fun findLineBreak(value: Int) =
    LineBreak.entries.find { it.ordinal == value } ?: LineBreak.None
