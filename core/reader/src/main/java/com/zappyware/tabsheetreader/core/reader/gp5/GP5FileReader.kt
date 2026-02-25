package com.zappyware.tabsheetreader.core.reader.gp5

import com.zappyware.tabsheetreader.core.data.DirectionSign
import com.zappyware.tabsheetreader.core.data.Directions
import com.zappyware.tabsheetreader.core.data.Equalizer
import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.Key
import com.zappyware.tabsheetreader.core.data.Lyrics
import com.zappyware.tabsheetreader.core.data.LyricsSequence
import com.zappyware.tabsheetreader.core.data.MasterEffect
import com.zappyware.tabsheetreader.core.data.MidiChannel
import com.zappyware.tabsheetreader.core.data.MidiChannels
import com.zappyware.tabsheetreader.core.data.Octave
import com.zappyware.tabsheetreader.core.data.Page
import com.zappyware.tabsheetreader.core.data.Song
import com.zappyware.tabsheetreader.core.data.SongInfo
import com.zappyware.tabsheetreader.core.data.Tempo
import com.zappyware.tabsheetreader.core.data.Track
import com.zappyware.tabsheetreader.core.data.TrackType
import com.zappyware.tabsheetreader.core.data.hasMasterEffect
import com.zappyware.tabsheetreader.core.reader.IFileReader
import com.zappyware.tabsheetreader.core.reader.clean
import com.zappyware.tabsheetreader.core.reader.readBoolean
import com.zappyware.tabsheetreader.core.reader.readByteSizeString
import com.zappyware.tabsheetreader.core.reader.readI16
import com.zappyware.tabsheetreader.core.reader.readI32
import com.zappyware.tabsheetreader.core.reader.readI8
import com.zappyware.tabsheetreader.core.reader.readIByteSizeString
import com.zappyware.tabsheetreader.core.reader.readIntSizeString
import com.zappyware.tabsheetreader.core.reader.toChannelValue
import java.io.InputStream
import javax.inject.Inject

class GP5FileReader @Inject constructor(): IFileReader {

    private var hasMasterEffect = false

    override suspend fun readSong(inputStream: InputStream): Song =
        Song(
            fileVersion = readFileVersion(inputStream).also { hasMasterEffect = it.hasMasterEffect() },
            songInfo = readSongInfo(inputStream),
            lyrics = readLyrics(inputStream),
            masterEffect = if (hasMasterEffect) readMasterEffect(inputStream) else null,
            page = readPage(inputStream),
            tempo = readTempo(inputStream),
            key = readKey(inputStream),
            octave = readOctave(inputStream),
            midiChannels = readMidiChannels(inputStream),
            directions = readDirections(inputStream),
            masterReverb = inputStream.readI32(),
            measureCount = inputStream.readI32(),
            tracksCount = inputStream.readI32(),
            // Faking tracks to test the NavigationBar
            tracks = TrackType.entries.mapIndexed { index, type -> Track(index.toLong(), type.name, type) },
        )

    private fun readFileVersion(inputStream: InputStream): FileVersion =
        FileVersion(
            version = inputStream.readByteSizeString(30)
        )

    private fun readSongInfo(inputStream: InputStream): SongInfo =
        SongInfo(
            title = inputStream.readIByteSizeString(),
            subTitle = inputStream.readIByteSizeString(),
            artist = inputStream.readIByteSizeString(),
            album = inputStream.readIByteSizeString(),
            words = inputStream.readIByteSizeString(),
            music = inputStream.readIByteSizeString(),
            copyright = inputStream.readIByteSizeString(),
            tab = inputStream.readIByteSizeString(),
            instructions = inputStream.readIByteSizeString(),
            notices = run {
                val noticeCount = inputStream.readI32()
                List(noticeCount) {
                    inputStream.readIByteSizeString()
                }
            }
        )

    private fun readLyrics(inputStream: InputStream): Lyrics =
        Lyrics(
            trackId = inputStream.readI32(),
            lines = List(5) {
                LyricsSequence(
                    startingMeasure = inputStream.readI32(),
                    text = inputStream.readIntSizeString().clean()
                )
            }
        )

    private fun readMasterEffect(inputStream: InputStream): MasterEffect =
        MasterEffect(
            volume = inputStream.readI32(),
            trackId = inputStream.readI32(),
            equalizer = readEqualizer(inputStream, 11)
        )

    private fun readEqualizer(inputStream: InputStream, knobs: Int): Equalizer =
        Equalizer(
            knobsVolume = List(knobs) {
                inputStream.readI8() / -10f
            }
        )

    private fun readPage(inputStream: InputStream): Page =
        Page(
            width = inputStream.readI32(),
            height = inputStream.readI32(),
            leftMargin = inputStream.readI32(),
            topMargin = inputStream.readI32(),
            rightMargin = inputStream.readI32(),
            bottomMargin = inputStream.readI32(),
            sizeProportion = inputStream.readI32() / 100f,
            header = inputStream.readI16(),
            title = inputStream.readIntSizeString(),
            subTitle = inputStream.readIntSizeString(),
            artist = inputStream.readIntSizeString(),
            album = inputStream.readIntSizeString(),
            words = inputStream.readIntSizeString(),
            music = inputStream.readIntSizeString(),
            wordsAndMusic = "${inputStream.readIntSizeString()}\n${inputStream.readIntSizeString()}",
            copyright = inputStream.readIntSizeString(),
            pageNumber = inputStream.readIntSizeString()
        )

    private fun readTempo(inputStream: InputStream): Tempo =
        Tempo(
            name = inputStream.readIntSizeString(),
            tempo = inputStream.readI32(),
            hide = if (hasMasterEffect) inputStream.readBoolean() else false
        )

    private fun readKey(inputStream: InputStream): Key =
        Key(
            value = inputStream.readI8(),
        )

    private fun readOctave(inputStream: InputStream): Octave =
        Octave(
            value = inputStream.readI32(),
        )

    private fun readMidiChannels(inputStream: InputStream): MidiChannels =
        MidiChannels(
            channels = List(64) { index ->
                MidiChannel(
                    channel = index + 1,
                    port = (index / 16) + 1,
                    instrument = inputStream.readI32(),
                    volume = inputStream.readI8().toChannelValue(),
                    balance = inputStream.readI8().toChannelValue(),
                    chorus = inputStream.readI8().toChannelValue(),
                    reverb = inputStream.readI8().toChannelValue(),
                    phaser = inputStream.readI8().toChannelValue(),
                    tremolo = inputStream.readI8().toChannelValue(),
                ).also {
                    inputStream.skip(2)
                }
            },
        )

    private fun readDirections(inputStream: InputStream): Directions =
        Directions(
            directions = mapOf(
                DirectionSign.CODA to inputStream.readI16(),
                DirectionSign.DOUBLE_CODA to inputStream.readI16(),
                DirectionSign.SEGNO to inputStream.readI16(),
                DirectionSign.SEGNO_SEGNO to inputStream.readI16(),
                DirectionSign.FINE to inputStream.readI16(),
                DirectionSign.DA_CAPO to inputStream.readI16(),
                DirectionSign.DA_CAPO_AL_CODA to inputStream.readI16(),
                DirectionSign.DA_CAPO_AL_DOUBLE_CODA to inputStream.readI16(),
                DirectionSign.DA_CAPO_AL_FINE to inputStream.readI16(),
                DirectionSign.DA_SEGNO to inputStream.readI16(),
                DirectionSign.DA_SEGNO_AL_CODA to inputStream.readI16(),
                DirectionSign.DA_SEGNO_AL_DOUBLE_CODA to inputStream.readI16(),
                DirectionSign.DA_SEGNO_AL_FINE to inputStream.readI16(),
                DirectionSign.DA_SEGNO_SEGNO to inputStream.readI16(),
                DirectionSign.DA_SEGNO_SEGNO_AL_CODA to inputStream.readI16(),
                DirectionSign.DA_SEGNO_SEGNO_AL_DOUBLE_CODA to inputStream.readI16(),
                DirectionSign.DA_SEGNO_SEGNO_AL_FINE to inputStream.readI16(),
                DirectionSign.DA_CODA to inputStream.readI16(),
                DirectionSign.DA_DOUBLE_CODA to inputStream.readI16()
            )
        )
}
