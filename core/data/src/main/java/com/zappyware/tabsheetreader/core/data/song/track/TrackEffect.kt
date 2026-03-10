package com.zappyware.tabsheetreader.core.data.song.track

import com.zappyware.tabsheetreader.core.data.song.Equalizer

data class TrackEffect(
    val instrument: RSEInstrument = RSEInstrument.Default,
    val equalizer: Equalizer = Equalizer.Default,
    val humanize: Int = 0,
    val autoAccentuation: Accentuation = Accentuation.NONE,
    val effectCategory: String? = null,
    val effect: String? = null,
) {
    companion object {
        val Default = TrackEffect()
    }
}
