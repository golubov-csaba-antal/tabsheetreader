package com.zappyware.tabsheetreader.core.reader.gp5

import com.zappyware.tabsheetreader.core.data.Song
import com.zappyware.tabsheetreader.core.data.song.Clipboard
import com.zappyware.tabsheetreader.core.data.song.Color
import com.zappyware.tabsheetreader.core.data.song.Directions
import com.zappyware.tabsheetreader.core.data.song.Equalizer
import com.zappyware.tabsheetreader.core.data.song.FileVersion
import com.zappyware.tabsheetreader.core.data.song.Key
import com.zappyware.tabsheetreader.core.data.song.Lyrics
import com.zappyware.tabsheetreader.core.data.song.LyricsSequence
import com.zappyware.tabsheetreader.core.data.song.MasterEffect
import com.zappyware.tabsheetreader.core.data.song.MidiChannel
import com.zappyware.tabsheetreader.core.data.song.MidiChannels
import com.zappyware.tabsheetreader.core.data.song.Octave
import com.zappyware.tabsheetreader.core.data.song.Page
import com.zappyware.tabsheetreader.core.data.song.SongInfo
import com.zappyware.tabsheetreader.core.data.song.Tempo
import com.zappyware.tabsheetreader.core.data.song.fromTremoloPickingEffect
import com.zappyware.tabsheetreader.core.data.song.fromTrillPeriod
import com.zappyware.tabsheetreader.core.data.song.hasMasterEffect
import com.zappyware.tabsheetreader.core.data.song.header.DirectionSign
import com.zappyware.tabsheetreader.core.data.song.header.Duration
import com.zappyware.tabsheetreader.core.data.song.header.KeySignatures
import com.zappyware.tabsheetreader.core.data.song.header.Marker
import com.zappyware.tabsheetreader.core.data.song.header.MeasureHeader
import com.zappyware.tabsheetreader.core.data.song.header.QUARTER_TIME
import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature
import com.zappyware.tabsheetreader.core.data.song.header.Tuplet
import com.zappyware.tabsheetreader.core.data.song.header.defaultBeams
import com.zappyware.tabsheetreader.core.data.song.header.findKeySignatures
import com.zappyware.tabsheetreader.core.data.song.header.findTripletFeel
import com.zappyware.tabsheetreader.core.data.song.header.length
import com.zappyware.tabsheetreader.core.data.song.header.time
import com.zappyware.tabsheetreader.core.data.song.isVersion5_0_0
import com.zappyware.tabsheetreader.core.data.song.measure.LineBreak
import com.zappyware.tabsheetreader.core.data.song.measure.Measure
import com.zappyware.tabsheetreader.core.data.song.measure.MeasureClef
import com.zappyware.tabsheetreader.core.data.song.measure.Voice
import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamHandler
import com.zappyware.tabsheetreader.core.data.song.measure.beat.ArtificialHarmonic
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Barre
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatDisplay
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStatuses
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStroke
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BeatStrokeDirections
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BendEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.BendPoint
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Chord
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Fingering
import com.zappyware.tabsheetreader.core.data.song.measure.beat.GraceEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.HarmonicEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.MixTableChange
import com.zappyware.tabsheetreader.core.data.song.measure.beat.MixTableItem
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NaturalHarmonic
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Note
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.NoteType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Octaves
import com.zappyware.tabsheetreader.core.data.song.measure.beat.PinchHarmonic
import com.zappyware.tabsheetreader.core.data.song.measure.beat.PitchClass
import com.zappyware.tabsheetreader.core.data.song.measure.beat.SemiHarmonic
import com.zappyware.tabsheetreader.core.data.song.measure.beat.SlapEffects
import com.zappyware.tabsheetreader.core.data.song.measure.beat.SlideType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TIED_NOTE
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TappedHarmonic
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TremoloPickingEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TrillEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.TupletBrackets
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Velocities
import com.zappyware.tabsheetreader.core.data.song.measure.beat.VoiceDirections
import com.zappyware.tabsheetreader.core.data.song.measure.beat.WahEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findBeatStatuses
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findBeatStrokeDirection
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findBendType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findChordAlteration
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findChordExtension
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findChordType
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findFingering
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findGraceEffectTransition
import com.zappyware.tabsheetreader.core.data.song.measure.beat.findSlapEffect
import com.zappyware.tabsheetreader.core.data.song.measure.beat.isEmpty
import com.zappyware.tabsheetreader.core.data.song.measure.beat.swapDirections
import com.zappyware.tabsheetreader.core.data.song.measure.findLineBreak
import com.zappyware.tabsheetreader.core.data.song.track.GuitarString
import com.zappyware.tabsheetreader.core.data.song.track.RSEInstrument
import com.zappyware.tabsheetreader.core.data.song.track.Track
import com.zappyware.tabsheetreader.core.data.song.track.TrackEffect
import com.zappyware.tabsheetreader.core.data.song.track.TrackSettings
import com.zappyware.tabsheetreader.core.data.song.track.findAccentuation
import com.zappyware.tabsheetreader.core.reader.IFileReader
import com.zappyware.tabsheetreader.core.reader.clean
import com.zappyware.tabsheetreader.core.reader.readBoolean
import com.zappyware.tabsheetreader.core.reader.readByteSizeString
import com.zappyware.tabsheetreader.core.reader.readF64
import com.zappyware.tabsheetreader.core.reader.readI16
import com.zappyware.tabsheetreader.core.reader.readI32
import com.zappyware.tabsheetreader.core.reader.readI8
import com.zappyware.tabsheetreader.core.reader.readIByteSizeString
import com.zappyware.tabsheetreader.core.reader.readIntSizeString
import com.zappyware.tabsheetreader.core.reader.readU8
import com.zappyware.tabsheetreader.core.reader.toChannelValue
import com.zappyware.tabsheetreader.core.reader.toRepeatAlternatives
import com.zappyware.tabsheetreader.core.reader.toStroke
import com.zappyware.tabsheetreader.core.reader.toVelocity
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.roundToInt

class GP5FileReader @Inject constructor(): IFileReader {

    private var hasMasterEffect = false
    private var isClipboard = false
    private var isVersion5_0_0 = false
    private var measureCount = 0
    private var tracksCount = 0
    private var headers = emptyList<MeasureHeader>()
    private var tracks = emptyList<Track>()

    override suspend fun readSong(inputStream: InputStream): Song =
        Song(
            fileVersion = readFileVersion(inputStream).also {
                hasMasterEffect = it.hasMasterEffect()
                isClipboard = it.version.startsWith("CLIPBOARD")
                isVersion5_0_0 = it.isVersion5_0_0()
            },
            clipboard = if (isClipboard) readClipboard(inputStream) else null,
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
            measureHeaders = readMeasureHeaders(inputStream).also { headers = it },
            tracks = readTracks(inputStream).also { tracks = it },
            measures = readMeasures(inputStream),
        )

    private fun readFileVersion(inputStream: InputStream): FileVersion =
        FileVersion(version = inputStream.readByteSizeString(30))

    private fun readClipboard(inputStream: InputStream): Clipboard =
        Clipboard(
            startMeasure = inputStream.readI32(),
            stopMeasure = inputStream.readI32(),
            startTrack = inputStream.readI32(),
            stopTrack = inputStream.readI32(),
            startBeat = inputStream.readI32(),
            stopBeat = inputStream.readI32(),
            subBarCopy = inputStream.readI32() == 1,
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
                List(noticeCount) { inputStream.readIByteSizeString() }
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
            gain = inputStream.readI32(),
            equalizer = readEqualizer(inputStream, 11)
        )

    private fun readEqualizer(inputStream: InputStream, knobs: Int): Equalizer =
        Equalizer(knobsVolume = List(knobs) { inputStream.readI8() / -10f })

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

    private fun readKey(inputStream: InputStream): Key = Key(value = inputStream.readI8())

    private fun readOctave(inputStream: InputStream): Octave = Octave(value = inputStream.readI32())

    private fun readMidiChannels(inputStream: InputStream): MidiChannels =
        MidiChannels(channels = List(64) { index -> readMidiChannel(inputStream, index) })

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
        ).also { inputStream.skip(2) }

    private fun readMidiChannel(inputStream: InputStream): MidiChannel =
        MidiChannel(channel = inputStream.readI32(), effectChannel = inputStream.readI32())

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
        var start = QUARTER_TIME
        var previousHeader: MeasureHeader? = null
        var currentTimeSignature: TimeSignature? = null
        var previousTimeSignature: TimeSignature? = null
        return List(measureCount) { index ->
            if (index != 0) inputStream.skip(1)
            flags = inputStream.readU8()
            MeasureHeader(
                number = index + 1,
                start = start,
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
                repeatAlternatives = if (flags and 0x10 == 0x10) inputStream.readU8().toRepeatAlternatives() else null,
                beams = if (flags and 0x03 == 0x03) List(4) { inputStream.readU8() } else previousHeader?.beams ?: defaultBeams,
                tripletFeel = (if (flags and 0x10 == 0) inputStream.skip(1) else Unit).let { findTripletFeel(inputStream.readU8()) },
            ).also {
                previousHeader = it
                previousTimeSignature = it.timeSignature
                start += it.length
            }
        }
    }

    private fun readMarker(inputStream: InputStream): Marker =
        Marker(title = inputStream.readIByteSizeString(), color = readColor(inputStream))

    private fun readColor(inputStream: InputStream): Color =
        Color(red = inputStream.readU8(), green = inputStream.readU8(), blue = inputStream.readU8()).also { inputStream.skip(1) }

    private fun readTracks(inputStream: InputStream): List<Track> {
        var flags: Int
        var settingsFlags: Int
        var stringCount: Int
        return List(tracksCount) { index ->
            if (isVersion5_0_0 || index == 0) inputStream.skip(1)
            flags = inputStream.readU8()
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
                strings = List(7) { stringIndex ->
                    val tuning = inputStream.readI32()
                    if (stringIndex < stringCount) GuitarString(number = stringIndex + 1, value = tuning) else null
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
                    autoAccentuation = findAccentuation(inputStream.readU8()).also { inputStream.readU8() },
                    humanize = inputStream.readU8().also {
                        inputStream.readI32()
                        inputStream.readI32()
                        inputStream.readI32()
                        inputStream.skip(12)
                    },
                    instrument = readRSEInstrument(inputStream),
                    equalizer = if (hasMasterEffect) readEqualizer(inputStream, 4) else Equalizer.Default,
                    effectCategory = if (hasMasterEffect) inputStream.readIByteSizeString() else null,
                    effect = if (hasMasterEffect) inputStream.readIByteSizeString() else null,
                ),
            )
        }.also { inputStream.skip(if (isVersion5_0_0) 2 else 1) }
    }

    private fun readMeasures(inputStream: InputStream): List<Measure> {
        return List(measureCount * tracksCount) { index ->
            val header = headers[index / tracksCount]
            val track = tracks[index % tracksCount]

            val partialMeasure = Measure(
                header = header,
                track = track,
                clef = MeasureClef.Treble,
                lineBreak = LineBreak.None,
                voices = emptyList()
            )

            val voices = List(2) { readVoice(inputStream, partialMeasure) }

            val lineBreak = findLineBreak(inputStream.readU8())
            partialMeasure.copy(voices = voices, lineBreak = lineBreak)
        }
    }

    private fun readVoice(inputStream: InputStream, measure: Measure): Voice {
        val partialVoice = Voice(
            measure = measure,
            beats = emptyList(),
            directions = VoiceDirections.None
        )

        var start = measure.header.start
        val beatsCount = inputStream.readI32()
        val beats = List(beatsCount) {
            readBeat(inputStream, start, partialVoice).also {
                start += if (it.status != BeatStatuses.Empty) {
                    it.duration.time
                } else {
                    0
                }
            }
        }

        val beamGroups = BeamHandler.createBeamGroups(beats, measure.header.timeSignature)

        return partialVoice.copy(
            beats = beats,
            beamGroups = beamGroups
        )
    }

    private fun readBeat(inputStream: InputStream, start: Int, voice: Voice): Beat {
        val flags = inputStream.readU8()

        var partialBeat = Beat(
            start = start,
            voice = voice,
            status = if (flags and 0x40 == 0x40) findBeatStatuses(inputStream.readU8()) else BeatStatuses.Normal,
            duration = readDuration(inputStream, flags),
            effect = if (flags and 0x02 == 0x02) {
                BeatEffect(
                    chord = readChord(inputStream, voice.measure.track.strings.size)
                )
            } else {
                BeatEffect.NONE
            },
            text = if (flags and 0x04 == 0x04) inputStream.readIByteSizeString() else null,
        )

        if (flags and 0x08 == 0x08) {
            val chord = readChord(inputStream, voice.measure.track.strings.size)
            val beatEffect = readBeatEffect(inputStream)
            partialBeat = partialBeat.copy(effect = beatEffect.copy(chord = chord))
        }

        if (flags and 0x10 == 0x10) {
            val mixTableChange = readMixTableChange(inputStream)
            partialBeat = partialBeat.copy(effect = partialBeat.effect.copy(mixTableChange = mixTableChange))
        }

        val stringFlags = inputStream.readU8()
        val notes = mutableListOf<Note>()

        voice.measure.track.strings.forEach { string ->
            if ((stringFlags and (1 shl (7 - string.number))) != 0) {
                notes.add(readNote(inputStream, string, partialBeat))
            }
        }

        val flags2 = inputStream.readI16()
        val octave = if (flags2 and 0x0010 == 0x0010) {
            Octaves.Ottava
        } else if (flags2 and 0x0020 == 0x0020) {
            Octaves.OttavaBassa
        } else if (flags2 and 0x0040 == 0x0040) {
            Octaves.Quindicesima
        } else if (flags2 and 0x0100 == 0x0100) {
            Octaves.QuindicesimaBassa
        } else {
            Octaves.None
        }

        val beatDisplay = BeatDisplay(
            breakBeam = flags2 and 0x0001 == 0x0001,
            forceBeam = flags2 and 0x0004 == 0x0004,
            forceBracket = flags2 and 0x2000 == 0x2000,
            breakSecondaryTuplet = flags2 and 0x1000 == 0x1000,
            beamDirections = if (flags2 and 0x0002 == 0x0002) {
                VoiceDirections.Down
            } else if (flags2 and 0x0008 == 0x0008) {
                VoiceDirections.Up
            } else {
                VoiceDirections.None
            },
            tupletBrackets = if (flags2 and 0x0200 == 0x0200) {
                TupletBrackets.Start
            } else if (flags2 and 0x0400 == 0x0400) {
                TupletBrackets.End
            } else {
                TupletBrackets.None
            },
            breakSecondary = if (flags2 and 0x0800 == 0x0800) {
                inputStream.readU8()
            } else {
                0
            }
        )

        return partialBeat.copy(
            notes = notes,
            octaves = octave,
            display = beatDisplay,
        )
    }

    private fun readBeatEffect(inputStream: InputStream): BeatEffect {
        val flags1 = inputStream.readI8()
        val flags2 = inputStream.readI8()
        return BeatEffect(
            vibrato = flags1 and 0x02 == 0x02,
            fadeIn = flags1 and 0x10 == 0x10,
            slapEffects = if (flags1 and 0x20 == 0x20) {
                findSlapEffect(inputStream.readI8())
            } else {
                SlapEffects.None
            },
            tremoloBar = if (flags2 and 0x04 == 0x04) {
                readTremoloBar(inputStream)
            } else {
                null
            },
            stroke = if (flags1 and 0x40 == 0x40) {
                readBeatStroke(inputStream)
            } else {
                BeatStroke.NONE
            },
            hasRasgueado = flags2 and 0x01 == 0x01,
            pickStroke = if (flags1 and 0x40 == 0x40) {
                val direction = inputStream.readI8()
                findBeatStrokeDirection(direction)
            } else {
                BeatStrokeDirections.None
            },
        )
    }

    private fun readTremoloBar(inputStream: InputStream): BendEffect? =
        readBendEffect(inputStream)

    private fun readBeatStroke(inputStream: InputStream): BeatStroke {
        val strokeDown = inputStream.readI8()
        val strokeUp = inputStream.readI8()
        val stroke = if (strokeUp > 0) {
            BeatStroke(
                value = strokeUp.toStroke(),
                direction = BeatStrokeDirections.Up,
            )
        } else if (strokeDown > 0) {
            BeatStroke(
                value = strokeDown.toStroke(),
                direction = BeatStrokeDirections.Down,
            )
        } else {
            BeatStroke.NONE
        }
        return stroke.swapDirections()
    }

    private fun readMixTableChange(inputStream: InputStream): MixTableChange {
        var tableChange = MixTableChange()
        tableChange = readMixTableChangeValues(inputStream, tableChange)
        tableChange = readMixTableChangeDurations(inputStream, tableChange)

        inputStream.readI8()

        val flags = inputStream.readI8()
        tableChange = readMixTableChangeFlags(tableChange, flags)

        val wah = readWahEffect(inputStream, flags)
        readRSEInstrumentEffect(inputStream, tableChange.rse)
        return tableChange.copy(wah = wah)
    }

    private fun readMixTableChangeValues(inputStream: InputStream, tableChange: MixTableChange): MixTableChange {
        val instrument = inputStream.readI8()
        val rse = readRSEInstrument(inputStream)
        if(isVersion5_0_0) inputStream.skip(1)
        val volume = inputStream.readI8()
        val balance = inputStream.readI8()
        val chorus = inputStream.readI8()
        val reverb = inputStream.readI8()
        val phaser = inputStream.readI8()
        val tremolo = inputStream.readI8()
        val tempoName = inputStream.readIByteSizeString()
        val tempo = inputStream.readI32()
        return tableChange.copy(
            instrument = if (instrument >= 0) {
                MixTableItem(instrument)
            } else {
                tableChange.instrument
            },
            rse = if (instrument >= 0) {
                rse
            } else {
                tableChange.rse
            },
            volume = if (volume >= 0) {
                MixTableItem(volume)
            } else {
                tableChange.volume
            },
            balance = if (balance >= 0) {
                MixTableItem(balance)
            } else {
                tableChange.balance
            },
            chorus = if (chorus >= 0) {
                MixTableItem(chorus)
            } else {
                tableChange.chorus
            },
            reverb = if (reverb >= 0) {
                MixTableItem(reverb)
            } else {
                tableChange.reverb
            },
            phaser = if (phaser >= 0) {
                MixTableItem(phaser)
            } else {
                tableChange.phaser
            },
            tremolo = if (tremolo >= 0) {
                MixTableItem(tremolo)
            } else {
                tableChange.tremolo
            },
            tempoName = if (tempo >= 0) {
                tempoName
            } else {
                tableChange.tempoName
            },
            tempo = if (tempo >= 0) {
                MixTableItem(tempo)
            } else {
                tableChange.tempo
            },
        )
    }

    private fun readMixTableChangeDurations(inputStream: InputStream, tableChange: MixTableChange): MixTableChange =
        tableChange.copy(
            volume = tableChange.volume?.copy(duration = inputStream.readI8()),
            balance = tableChange.balance?.copy(duration = inputStream.readI8()),
            chorus = tableChange.chorus?.copy(duration = inputStream.readI8()),
            reverb = tableChange.reverb?.copy(duration = inputStream.readI8()),
            phaser = tableChange.phaser?.copy(duration = inputStream.readI8()),
            tremolo = tableChange.tremolo?.copy(duration = inputStream.readI8()),
            tempo = tableChange.tempo?.copy(duration = inputStream.readI8()),
            hideTempo = tableChange.tempo?.let { hasMasterEffect && inputStream.readBoolean() } ?: tableChange.hideTempo,
        )

    private fun readMixTableChangeFlags(tableChange: MixTableChange, flags: Int): MixTableChange =
        tableChange.copy(
            volume = tableChange.volume?.copy(allTracks = flags and 0x01 == 0x01),
            balance = tableChange.balance?.copy(allTracks = flags and 0x02 == 0x02),
            chorus = tableChange.chorus?.copy(allTracks = flags and 0x04 == 0x04),
            reverb = tableChange.reverb?.copy(allTracks = flags and 0x08 == 0x08),
            phaser = tableChange.phaser?.copy(allTracks = flags and 0x10 == 0x10),
            tremolo = tableChange.tremolo?.copy(allTracks = flags and 0x20 == 0x20),
            useRSE = flags and 0x40 == 0x40,
        )

    private fun readRSEInstrument(inputStream: InputStream): RSEInstrument =
        RSEInstrument(
            instrument = inputStream.readI32(),
            unknown = inputStream.readI32(),
            soundBank = inputStream.readI32(),
            effectNumber = if (isVersion5_0_0) {
                inputStream.readI16().also { inputStream.skip(1) }
            } else {
                inputStream.readI32()
            }
        )

    private fun readRSEInstrumentEffect(inputStream: InputStream, rse: RSEInstrument): RSEInstrument =
        if (hasMasterEffect) {
            val effect = inputStream.readIByteSizeString()
            val effectCategory = inputStream.readIByteSizeString()
            rse.copy(effect = effect, effectCategory = effectCategory)
        } else {
            rse
        }

    private fun readChord(inputStream: InputStream, size: Int): Chord? {
        val isInNewFormat = inputStream.readBoolean()
        var firstFret: Int
        return if (isInNewFormat) {
            var intonation: String
            Chord(
                length = size,
                newFormat = true,
                sharp = inputStream.readBoolean().also {
                    intonation = if (it) "sharp" else "flat"
                    inputStream.skip(3)
                },
                root = PitchClass(inputStream.readU8(), intonation),
                type = findChordType(inputStream.readU8()),
                extension = findChordExtension(inputStream.readU8()),
                bass = PitchClass(inputStream.readI32(), intonation),
                tonality = findChordAlteration(inputStream.readI32()),
                add = inputStream.readBoolean(),
                name = inputStream.readByteSizeString(22),
                fifth = findChordAlteration(inputStream.readU8()),
                ninth = findChordAlteration(inputStream.readU8()),
                eleventh = findChordAlteration(inputStream.readU8()),
                firstFret = inputStream.readI32(),
                strings = List(7) { inputStream.readI32() }.take(size),
                barres = run {
                    val barresCount = inputStream.readU8()
                    val barreFrets = List(5) { inputStream.readU8() }
                    val barreStarts = List(5) { inputStream.readU8() }
                    val barreEnds = List(5) { inputStream.readU8() }
                    List(barresCount.coerceAtMost(5)) { i ->
                        Barre(fret = barreFrets[i], start = barreStarts[i], end = barreEnds[i])
                    }
                },
                omissions = List(7) { inputStream.readBoolean() }.also { inputStream.skip(1) },
                fingerings = List(7) { findFingering(inputStream.readI8()) },
                show = inputStream.readBoolean(),
            )
        } else {
            Chord(
                length = size,
                name = inputStream.readIByteSizeString(),
                firstFret = inputStream.readI32().also { firstFret = it },
                strings = if (firstFret < 0) {
                    emptyList()
                } else {
                    List(6) { inputStream.readI32() }.take(size)
                },
            )
        }
    }

    private fun readDuration(inputStream: InputStream, flags: Int): Duration {
        val durationByte = inputStream.readI8()
        val value = 1 shl (durationByte + 2)
        val isDotted = flags and 0x01 == 0x01

        val tuplet = if (flags and 0x20 == 0x20) {
            val iTuplet = inputStream.readI32()
            when (iTuplet) {
                3 -> Tuplet(enters = 3, times = 2)
                5 -> Tuplet(enters = 5, times = 4)
                6 -> Tuplet(enters = 6, times = 4)
                7 -> Tuplet(enters = 7, times = 4)
                9 -> Tuplet(enters = 9, times = 8)
                10 -> Tuplet(enters = 10, times = 8)
                11 -> Tuplet(enters = 11, times = 8)
                12 -> Tuplet(enters = 12, times = 8)
                13 -> Tuplet(enters = 13, times = 8)
                else -> Tuplet.Default
            }
        } else {
            Tuplet.Default
        }

        return Duration(
            value = value,
            isDotted = isDotted,
            tuplet = tuplet
        )
    }

    private fun readWahEffect(inputStream: InputStream, flags: Int): WahEffect =
        WahEffect(
            value = inputStream.readI8(),
            display = flags and 0x80 == 0x80
        )

    private fun readNote(inputStream: InputStream, string: GuitarString, beat: Beat): Note {
        val flag = inputStream.readU8()
        val noteType = if (flag and 0x20 == 0x20) findNoteType(inputStream.readU8()) else NoteType.Normal
        val velocity = if (flag and 0x10 == 0x10) inputStream.readI8().toVelocity() else Velocities.DEFAULT
        val value = if (flag and 0x20 == 0x20) {
            val fret = inputStream.readI8()
            if (noteType == NoteType.Tie) {
                TIED_NOTE
            } else {
                fret
            }.coerceIn(0, 100)
        } else  {
            0
        }
        val leftFingering = if (flag and 0x80 == 0x80) findFingering(inputStream.readI8()) else Fingering.Open
        val rightFingering = if (flag and 0x80 == 0x80) findFingering(inputStream.readI8()) else Fingering.Open
        val durationPercent = if (flag and 0x01 == 0x01) inputStream.readF64().toFloat() else 1f
        
        val flag2 = inputStream.readU8()
        val swapAccidentals = flag2 and 0x02 == 0x02

        val effect = if (flag and 0x08 == 0x08) {
            val flag1 = inputStream.readI8()
            val flag2 = inputStream.readI8()
            NoteEffect(
                heavyAccentuatedNote = flag and 0x02 == 0x02,
                ghostNote = flag and 0x04 == 0x04,
                accentuatedNote = flag and 0x40 == 0x40,
                hammer = flag1 and 0x02 == 0x02,
                letRing = flag1 and 0x08 == 0x08,
                staccato = flag2 and 0x01 == 0x01,
                palmMute = flag2 and 0x02 == 0x02,
                vibrato = flag2 and 0x40 == 0x40,
                bend = if (flag1 and 0x01 == 0x01) readBendEffect(inputStream) else null,
                grace = if (flag1 and 0x10 == 0x10) readGraceEffect(inputStream) else null,
                tremoloPicking = if (flag2 and 0x04 == 0x04) readTremoloPickingEffect(inputStream) else null,
                slides = if (flag2 and 0x08 == 0x08) readSlides(inputStream) else emptyList(),
                harmonic = if (flag2 and 0x10 == 0x10) readHarmonic(inputStream) else null,
                trill = if (flag2 and 0x20 == 0x20) readTrillEffect(inputStream) else null,
                leftHandFinger = leftFingering,
                rightHandFinger = rightFingering,
            )
        } else {
            NoteEffect.DEFAULT.copy(
                heavyAccentuatedNote = flag and 0x02 == 0x02,
                ghostNote = flag and 0x04 == 0x04,
                accentuatedNote = flag and 0x40 == 0x40,
                leftHandFinger = leftFingering,
                rightHandFinger = rightFingering,
            )
        }

        return Note(
            beat = beat,
            string = string.number,
            effect = effect,
            type = noteType,
            velocity = velocity,
            value = value,
            durationPercent = durationPercent,
            swapAccidentals = swapAccidentals,
        )
    }

    private fun findNoteType(value: Int): NoteType =
        when (value) {
            1 -> NoteType.Normal
            2 -> NoteType.Tie
            3 -> NoteType.Dead
            else -> NoteType.Rest
        }

    private fun readBendEffect(inputStream: InputStream): BendEffect? {
        val effect = BendEffect(
            type = findBendType(inputStream.readI8()),
            value = inputStream.readI32(),
            points = List(inputStream.readI32()) {
                BendPoint(
                    position = (inputStream.readI32() * BendEffect.MAX_POSITION / 60f).roundToInt(),
                    value = (inputStream.readI32() * BendEffect.SEMITONE_LENGTH / 25f).roundToInt(),
                    vibrato = inputStream.readBoolean(),
                )
            }
        )
        return if (effect.isEmpty()) null else effect
    }

    private fun readGraceEffect(inputStream: InputStream): GraceEffect {
        val fret = inputStream.readU8()
        val velocity = inputStream.readU8()
        val transition = findGraceEffectTransition(inputStream.readU8())
        val duration = inputStream.readU8()
        val flag = inputStream.readU8()
        return GraceEffect(
            fret = fret,
            velocity = velocity,
            transition = transition,
            duration = 1 shl (7 - duration),
            isDead = flag and 0x01 == 0x01,
            isOnBeat = flag and 0x02 == 0x02,
        )
    }

    private fun readSlides(inputStream: InputStream): List<SlideType> {
        val slideType = inputStream.readU8()
        val slides = mutableListOf<SlideType>()
        if (slideType and 0x01 == 0x01) slides.add(SlideType.ShiftSlideTo)
        if (slideType and 0x02 == 0x02) slides.add(SlideType.LegatoSlideTo)
        if (slideType and 0x04 == 0x04) slides.add(SlideType.OutDownwards)
        if (slideType and 0x08 == 0x08) slides.add(SlideType.OutUpwards)
        if (slideType and 0x10 == 0x10) slides.add(SlideType.IntoFromBelow)
        if (slideType and 0x20 == 0x20) slides.add(SlideType.IntoFromAbove)
        return slides.toList()
    }

    private fun readHarmonic(inputStream: InputStream): HarmonicEffect? {
        val type = inputStream.readI8()
        return when(type) {
            1 -> NaturalHarmonic
            2 -> ArtificialHarmonic(
                PitchClass(
                    semitone = inputStream.readU8(),
                    intonation = if (inputStream.readI8() == 1) "sharp" else "flat",
                ),
                octave = Octave(inputStream.readU8()),
            )
            3 -> TappedHarmonic(fret = inputStream.readU8())
            4 -> PinchHarmonic
            5 -> SemiHarmonic
            else -> null
        }
    }

    private fun readTremoloPickingEffect(inputStream: InputStream): TremoloPickingEffect =
        TremoloPickingEffect(
            duration = Duration(fromTremoloPickingEffect(inputStream.readI8()).value)
        )

    private fun readTrillEffect(inputStream: InputStream): TrillEffect =
        TrillEffect(
            fret = inputStream.readI8(),
            duration = Duration(fromTrillPeriod(inputStream.readI8()).value)
        )
}
