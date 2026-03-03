package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class NoteType {
    Rest,
    Normal,
    Tie,
    Dead,
}

fun findNoteType(value: Int) = NoteType.entries.find { it.ordinal == value } ?: NoteType.Rest
