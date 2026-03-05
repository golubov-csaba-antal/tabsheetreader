package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class GraceEffectTransition {
    None,
    Slide,
    Bend,
    Hammer,
}

fun findGraceEffectTransition(value: Int) =
    GraceEffectTransition.entries.firstOrNull { it.ordinal == value } ?: GraceEffectTransition.None
