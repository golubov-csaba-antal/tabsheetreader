package com.zappyware.tabsheetreader.core.data

data class Track(
    val number: Int,
    val isPercussionTrack: Boolean,
    val is12StringedGuitarTrack: Boolean,
    val isBanjoTrack: Boolean,
    val isVisible: Boolean,
    val isSolo: Boolean,
    val isMute: Boolean,
    val useRSE: Boolean,
    val indicateTuning: Boolean,
    val name: String,
    val stringCount: Int,
    val strings: List<GuitarString>,
    val port: Int,
    val channel: MidiChannel,
    val fretCount: Int,
    val offset: Int,
    val color: Color,
    val settings: TrackSettings,
    val rse: TrackEffect,
)
