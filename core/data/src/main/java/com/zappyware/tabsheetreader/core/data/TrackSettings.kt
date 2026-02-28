package com.zappyware.tabsheetreader.core.data

data class TrackSettings(
    val tablature: Boolean,
    val notation: Boolean,
    val areDiagramsBelow: Boolean,
    val showRhythm: Boolean,
    val forceHorizontal: Boolean,
    val forceChannels: Boolean,
    val diagramList: Boolean,
    val diagramsInScore: Boolean,
    val muted: Boolean,
    val autoLetRing: Boolean,
    val autoBrush: Boolean,
    val extendRhythmic: Boolean,
)
