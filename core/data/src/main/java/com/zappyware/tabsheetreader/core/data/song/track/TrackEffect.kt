package com.zappyware.tabsheetreader.core.data.song.track

import com.zappyware.tabsheetreader.core.data.song.Equalizer

data class TrackEffect(
    val instrument: Instrument,
    val equalizer: Equalizer? = null,
    val humanize: Int,
    val autoAccentuation: Accentuation,
    val effectCategory: String? = null,
    val effect: String? = null,
)
