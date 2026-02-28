package com.zappyware.tabsheetreader.core.data

enum class Accentuation {
    NONE,
    VERY_SOFT,
    SOFT,
    MEDIUM,
    STRONG,
    VERY_STRONG,
}

fun findAccentuation(value: Int) = Accentuation.entries.firstOrNull { it.ordinal == value } ?: Accentuation.NONE
