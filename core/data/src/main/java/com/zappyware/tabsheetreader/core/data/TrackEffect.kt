package com.zappyware.tabsheetreader.core.data

data class TrackEffect(
    val instrument: Instrument,
    val equalizer: Equalizer? = null,
    val humanize: Int,
    val autoAccentuation: Accentuation,
    val effectCategory: String? = null,
    val effect: String? = null,
)
