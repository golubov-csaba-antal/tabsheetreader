package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class SlapEffects {
    None,
    Tapping,
    Slapping,
    Popping,
}

fun findSlapEffect(value: Int) =
    SlapEffects.entries.firstOrNull { it.ordinal == value } ?: SlapEffects.None
