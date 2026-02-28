package com.zappyware.tabsheetreader.core.data

data class Song(
    val fileVersion: FileVersion,
    val songInfo: SongInfo,
    val lyrics: Lyrics,
    val masterEffect: MasterEffect?,
    val page: Page,
    val tempo: Tempo,
    val key: Key,
    val octave: Octave,
    val midiChannels: MidiChannels,
    val directions: Directions,
    val masterReverb: Int,
    val measureCount: Int,
    val tracksCount: Int,
    val measureHeaders: List<MeasureHeader>,
    val tracks: List<Track>,
)
