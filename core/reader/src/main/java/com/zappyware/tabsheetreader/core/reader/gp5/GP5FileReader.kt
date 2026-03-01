package com.zappyware.tabsheetreader.core.reader.gp5

import com.zappyware.tabsheetreader.core.data.Color
import com.zappyware.tabsheetreader.core.data.DirectionSign
import com.zappyware.tabsheetreader.core.data.Directions
import com.zappyware.tabsheetreader.core.data.Duration
import com.zappyware.tabsheetreader.core.data.Equalizer
import com.zappyware.tabsheetreader.core.data.FileVersion
import com.zappyware.tabsheetreader.core.data.GuitarString
import com.zappyware.tabsheetreader.core.data.Instrument
import com.zappyware.tabsheetreader.core.data.Key
import com.zappyware.tabsheetreader.core.data.KeySignatures
import com.zappyware.tabsheetreader.core.data.Lyrics
import com.zappyware.tabsheetreader.core.data.LyricsSequence
import com.zappyware.tabsheetreader.core.data.Marker
import com.zappyware.tabsheetreader.core.data.MasterEffect
import com.zappyware.tabsheetreader.core.data.MeasureHeader
import com.zappyware.tabsheetreader.core.data.MidiChannel
import com.zappyware.tabsheetreader.core.data.MidiChannels
import com.zappyware.tabsheetreader.core.data.Octave
import com.zappyware.tabsheetreader.core.data.Page
import com.zappyware.tabsheetreader.core.data.Song
import com.zappyware.tabsheetreader.core.data.SongInfo
import com.zappyware.tabsheetreader.core.data.Tempo
import com.zappyware.tabsheetreader.core.data.TimeSignature
import com.zappyware.tabsheetreader.core.data.Track
import com.zappyware.tabsheetreader.core.data.TrackEffect
import com.zappyware.tabsheetreader.core.data.TrackSettings
import com.zappyware.tabsheetreader.core.data.Tuplet
import com.zappyware.tabsheetreader.core.data.defaultBeams
import com.zappyware.tabsheetreader.core.data.findAccentuation
import com.zappyware.tabsheetreader.core.data.findKeySignatures
import com.zappyware.tabsheetreader.core.data.findTripletFeel
import com.zappyware.tabsheetreader.core.data.hasMasterEffect
import com.zappyware.tabsheetreader.core.data.isVersion5_0_0
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
import com.zappyware.tabsheetreader.core.reader.toRepeatAlternatives
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

class GP5FileReader @Inject constructor(): IFileReader {

    private var hasMasterEffect = false

    private var isVersion5_0_0 = false

    private var measureCount = 0

    private var tracksCount = 0

    override suspend fun readSong(inputStream: InputStream): Song =
        Song(
            fileVersion = readFileVersion(inputStream).also {
                hasMasterEffect = it.hasMasterEffect()
                isVersion5_0_0 = it.isVersion5_0_0()
            },
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
            measureCount = inputStream.readI32().also { measureCount = it },
            tracksCount = inputStream.readI32().also { tracksCount = it },
            measureHeaders = readMeasureHeaders(inputStream),
            tracks = readTracks(inputStream),
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
                readMidiChannel(inputStream, index)
            },
        )

    private fun readMidiChannel(inputStream: InputStream, index: Int): MidiChannel =
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

    private fun readMidiChannel(inputStream: InputStream): MidiChannel =
        MidiChannel(
            channel = inputStream.readI32(),
            effectChannel = inputStream.readI32(),
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

    private fun readMeasureHeaders(inputStream: InputStream): List<MeasureHeader> {
        var flags: Int
        var previousHeader: MeasureHeader? = null
        var currentTimeSignature: TimeSignature? = null
        var previousTimeSignature: TimeSignature? = null
        return List(measureCount) { index ->
            flags = inputStream.read()
            MeasureHeader(
                number = index + 1,
                start = 0,
                timeSignature = TimeSignature(
                    numerator = if (flags and 0x01 == 0x01) inputStream.read() else (previousHeader?.timeSignature?.numerator ?: 0),
                    denominator = Duration(
                        value = if (flags and 0x02 == 0x02) inputStream.read() else (previousHeader?.timeSignature?.denominator?.value ?: 0),
                        isDotted = false,
                        tuplet = Tuplet(1, 1),
                    ),
                ).also { currentTimeSignature = it },
                timeSignatureChanged = previousTimeSignature?.numerator != currentTimeSignature?.numerator && previousTimeSignature?.denominator?.value != currentTimeSignature?.denominator?.value,
                isRepeatOpen = flags and 0x04 == 0x04,
                hasDoubleBar = flags and 0x80 == 0x80,
                repeatClose = if (flags and 0x08 == 0x08) inputStream.read() else 0,
                marker = if (flags and 0x20 == 0x20) readMarker(inputStream) else null,
                keySignature = if (flags and 0x40 == 0x40) findKeySignatures(inputStream.read(), inputStream.read()) else (previousHeader?.keySignature ?: KeySignatures.CMajor),
                repeatAlternatives = if (flags and 0x10 == 0x10) inputStream.read().toRepeatAlternatives() else null,
                beams = if (flags and 0x03 == 0x03) listOf(inputStream.read(), inputStream.read(), inputStream.read(), inputStream.read()) else previousHeader?.beams ?: defaultBeams,
                tripletFeel = (if (flags and 0x10 == 0) inputStream.skip(1) else Unit).let { findTripletFeel(inputStream.read()) },
            ).also {
                previousHeader = it
                previousTimeSignature = it.timeSignature
                inputStream.skip(1)
            }
        }
    }

    private fun readMarker(inputStream: InputStream): Marker =
        Marker(
            title = inputStream.readIByteSizeString(),
            color = readColor(inputStream),
        )

    private fun readColor(inputStream: InputStream): Color =
        Color(
            red = inputStream.read(),
            green = inputStream.read(),
            blue = inputStream.read(),
        ).also {
            inputStream.skip(1)
        }

    private fun readTracks(inputStream: InputStream): List<Track> {
        var flags: Int
        var settingsFlags: Int
        var stringCount: Int
        return List(tracksCount) { index ->
            if (isVersion5_0_0 && index == 0) inputStream.skip(1)
            flags = inputStream.read()
            Track(
                number = index + 1,
                isPercussionTrack = flags and 0x01 == 0x01,
                is12StringedGuitarTrack = flags and 0x02 == 0x02,
                isBanjoTrack = flags and 0x04 == 0x04,
                isVisible = flags and 0x08 == 0x08,
                isSolo = flags and 0x13 == 0x10,
                isMute = flags and 0x20 == 0x20,
                useRSE = flags and 0x40 == 0x40,
                indicateTuning = flags and 0x80 == 0x80,
                name = inputStream.readByteSizeString(40),
                stringCount = inputStream.readI32().also { stringCount = it },
                strings = List(7) { index ->
                    val tuning = inputStream.readI32()
                    if(index < stringCount) {
                        GuitarString(
                            number = index + 1,
                            value = tuning,
                        )
                    } else {
                        null
                    }
                }.filterNotNull(),
                port = inputStream.readI32(),
                channel = readMidiChannel(inputStream),
                fretCount = inputStream.readI32(),
                offset = inputStream.readI32(),
                color = readColor(inputStream).also { settingsFlags = inputStream.readI16() },
                settings = TrackSettings(
                    tablature = settingsFlags and 0x0001 == 0x0001,
                    notation = settingsFlags and 0x0002 == 0x0002,
                    areDiagramsBelow = settingsFlags and 0x0004 == 0x0004,
                    showRhythm = settingsFlags and 0x0008 == 0x0008,
                    forceHorizontal = settingsFlags and 0x0010 == 0x0010,
                    forceChannels = settingsFlags and 0x0020 == 0x0020,
                    diagramList = settingsFlags and 0x0040 == 0x0040,
                    diagramsInScore = settingsFlags and 0x0080 == 0x0080,
                    muted = settingsFlags and 0x0100 == 0x0100,
                    autoLetRing = settingsFlags and 0x0200 == 0x0200,
                    autoBrush = settingsFlags and 0x0400 == 0x0400,
                    extendRhythmic = settingsFlags and 0x0800 == 0x0800,
                ),
                rse = TrackEffect(
                    autoAccentuation = findAccentuation(inputStream.readI8()).also { inputStream.skip(1) },
                    humanize = inputStream.readI8().also {
                        inputStream.readI32()
                        inputStream.readI32()
                        inputStream.readI32()
                        inputStream.skip(12)
                    },
                    instrument = Instrument(
                        instrument = inputStream.readI32(),
                        unknown = inputStream.readI32(),
                        soundBank = inputStream.readI32(),
                        effectNumber = if (isVersion5_0_0) {
                            inputStream.readI16().also { inputStream.skip(1) }
                        } else {
                            inputStream.readI32()
                        },
                    ),
                    equalizer = if (hasMasterEffect) {
                        readEqualizer(inputStream, 4)
                    } else {
                        null
                    },
                    effectCategory = if (hasMasterEffect) inputStream.readIByteSizeString() else null,
                    effect = if (hasMasterEffect) inputStream.readIByteSizeString() else null,
                ),
            )
        }.also {
            if (isVersion5_0_0) inputStream.skip(2) else inputStream.skip(1)
        }
    }
}
