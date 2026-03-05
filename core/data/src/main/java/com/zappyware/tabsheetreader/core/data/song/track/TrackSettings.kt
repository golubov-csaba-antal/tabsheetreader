package com.zappyware.tabsheetreader.core.data.song.track

data class TrackSettings(
    val tablature: Boolean = true,
    val notation: Boolean = true,
    val areDiagramsBelow: Boolean = false,
    val showRhythm: Boolean = false,
    val forceHorizontal: Boolean = false,
    val forceChannels: Boolean = false,
    val diagramList: Boolean = true,
    val diagramsInScore: Boolean = false,
    val muted: Boolean = false,
    val autoLetRing: Boolean = false,
    val autoBrush: Boolean = false,
    val extendRhythmic: Boolean = false,
) {
    companion object {
        val Default = TrackSettings()
    }
}
