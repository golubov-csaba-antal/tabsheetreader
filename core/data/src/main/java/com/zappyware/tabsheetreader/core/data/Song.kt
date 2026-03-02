package com.zappyware.tabsheetreader.core.data

import com.zappyware.tabsheetreader.core.data.song.Directions
import com.zappyware.tabsheetreader.core.data.song.FileVersion
import com.zappyware.tabsheetreader.core.data.song.Key
import com.zappyware.tabsheetreader.core.data.song.Lyrics
import com.zappyware.tabsheetreader.core.data.song.MasterEffect
import com.zappyware.tabsheetreader.core.data.song.MidiChannels
import com.zappyware.tabsheetreader.core.data.song.Octave
import com.zappyware.tabsheetreader.core.data.song.Page
import com.zappyware.tabsheetreader.core.data.song.SongInfo
import com.zappyware.tabsheetreader.core.data.song.Tempo
import com.zappyware.tabsheetreader.core.data.song.header.MeasureHeader
import com.zappyware.tabsheetreader.core.data.song.measure.Measure
import com.zappyware.tabsheetreader.core.data.song.track.Track

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
    val measures: List<Measure>,
    val tracks: List<Track>,
)
