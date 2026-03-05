package com.zappyware.tabsheetreader.core.reader

import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.header.Duration
import com.zappyware.tabsheetreader.core.data.song.header.MeasureHeader
import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature
import com.zappyware.tabsheetreader.core.data.song.measure.LineBreak
import com.zappyware.tabsheetreader.core.data.song.measure.Measure
import com.zappyware.tabsheetreader.core.data.song.measure.MeasureClef
import com.zappyware.tabsheetreader.core.data.song.measure.Voice
import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamHandler
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import com.zappyware.tabsheetreader.core.data.song.track.Track
import org.junit.Assert
import org.junit.Test

class BeamHandlerTest {

    private fun createFakeMeasure(timeSignature: TimeSignature) =
        Measure(
            header = MeasureHeader(timeSignature = timeSignature),
            track = Track(),
            clef = MeasureClef.Treble,
            lineBreak = LineBreak.None,
            voices = emptyList(),
        )

    @Test
    fun `Test if quarter beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = List(4) {
            Beat(
                voice = voice,
                duration = Duration.Default,
            )
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 1 })
    }

    @Test
    fun `Test if eight beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = List(8) {
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            )
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 2 })
    }

    @Test
    fun `Test if quarter and eight beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = List(4) {
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            )
        } + List(1) {
            Beat(
                voice = voice,
                duration = Duration(Durations.QUARTER.value),
            )
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(3, beams.size)
        Assert.assertTrue(beams[0].beams.size == 2 && beams[1].beams.size == 2 && beams.last().beams.size == 1)
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 1st`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = listOf(
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 3 })
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 2nd`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = listOf(
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(
            beams[0].beams.size == 3 &&
                    beams[1].beams.size == 2 &&
                    beams[2].beams.size == 3 &&
                    beams[3].beams.size == 2
        )
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 3rd`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = listOf(
            Beat(
                voice = voice,
                duration = Duration(Durations.HALF.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(3, beams.size)
        Assert.assertTrue(
            beams[0].beams.size == 1 &&
                    beams[1].beams.size == 2 &&
                    beams[2].beams.size == 5
        )
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 4th`() {
        val timeSignature = TimeSignature.Default
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = listOf(
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.THIRTYSECOND.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.SIXTEENTH.value),
            ),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 4 })
    }

    @Test
    fun `Test if complex beats are aligned in 6-8 properly 1st`() {
        val timeSignature = TimeSignature(6, Duration(Durations.EIGHTH.value))
        val measure = createFakeMeasure(timeSignature)
        val voice = Voice(measure = measure)
        val beats = listOf(
            Beat(
                voice = voice,
                duration = Duration(Durations.QUARTER.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
            Beat(
                voice = voice,
                duration = Duration(Durations.EIGHTH.value),
            ),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(2, beams.size)
        Assert.assertTrue(beams[0].beams.size == 2 && beams[1].beams.size == 3)
    }
}
