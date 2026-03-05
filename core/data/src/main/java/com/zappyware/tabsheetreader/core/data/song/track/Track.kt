package com.zappyware.tabsheetreader.core.data.song.track

import com.zappyware.tabsheetreader.core.data.song.Color
import com.zappyware.tabsheetreader.core.data.song.MidiChannel

data class Track(
    val number: Int = 1,
    val isPercussionTrack: Boolean = false,
    val is12StringedGuitarTrack: Boolean = false,
    val isBanjoTrack: Boolean = false,
    val isVisible: Boolean = true,
    val isSolo: Boolean = false,
    val isMute: Boolean = false,
    val useRSE: Boolean = false,
    val indicateTuning: Boolean = false,
    val name: String = "Track 1",
    val stringCount: Int = 0,
    val strings: List<GuitarString> = emptyList(),
    val port: Int = 1,
    val channel: MidiChannel = MidiChannel.Default,
    val fretCount: Int = 24,
    val offset: Int = 0,
    val color: Color = Color.Red,
    val settings: TrackSettings = TrackSettings.Default,
    val rse: TrackEffect = TrackEffect.Default,
)
