package com.zappyware.tabsheetreader.core.reader

import com.zappyware.tabsheetreader.core.data.song.Durations
import com.zappyware.tabsheetreader.core.data.song.header.Duration
import com.zappyware.tabsheetreader.core.data.song.header.TimeSignature
import com.zappyware.tabsheetreader.core.data.song.measure.beam.BeamHandler
import com.zappyware.tabsheetreader.core.data.song.measure.beat.Beat
import org.junit.Assert
import org.junit.Test

class BeamHandlerTest {

    @Test
    fun `Test if quarter beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val beats = List(4) {
            Beat(duration = Duration.Default,)
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 1 })
    }

    @Test
    fun `Test if eight beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val beats = List(8) {
            Beat(duration = Duration(Durations.EIGHTH.value),)
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 2 })
    }

    @Test
    fun `Test if quarter and eight beats are aligned in 4-4 properly`() {
        val timeSignature = TimeSignature.Default
        val beats = List(4) {
            Beat(duration = Duration(Durations.EIGHTH.value),)
        } + List(1) {
            Beat(duration = Duration(Durations.QUARTER.value),)
        }

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(3, beams.size)
        Assert.assertTrue(beams[0].beams.size == 2 && beams[1].beams.size == 2 && beams.last().beams.size == 1)
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 1st`() {
        val timeSignature = TimeSignature.Default
        val beats = listOf(
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(
            beams[0].beams.size == 3 &&
                    beams[1].beams.size == 3 &&
                    beams[2].beams.size == 2 &&
                    beams[3].beams.size == 4
        )
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 2nd`() {
        val timeSignature = TimeSignature.Default
        val beats = listOf(
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(
            beams[0].beams.size == 2 &&
                    beams[1].beams.size == 2 &&
                    beams[2].beams.size == 2 &&
                    beams[3].beams.size == 4
        )
    }

    @Test
    fun `Test if complex beats are aligned in 4-4 properly 3rd`() {
        val timeSignature = TimeSignature.Default
        val beats = listOf(
            Beat(duration = Duration(Durations.HALF.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
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
        val beats = listOf(
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
            Beat(duration = Duration(Durations.THIRTYSECOND.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
            Beat(duration = Duration(Durations.SIXTEENTH.value),),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(4, beams.size)
        Assert.assertTrue(beams.all { it.beams.size == 4 })
    }

    @Test
    fun `Test if complex beats are aligned in 6-8 properly 1st`() {
        val timeSignature = TimeSignature(6, Duration(Durations.EIGHTH.value))
        val beats = listOf(
            Beat(duration = Duration(Durations.QUARTER.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
            Beat(duration = Duration(Durations.EIGHTH.value),),
        )

        val beams = BeamHandler.createBeamGroups(beats, timeSignature)
        Assert.assertEquals(2, beams.size)
        Assert.assertTrue(beams[0].beams.size == 2 && beams[1].beams.size == 3)
    }
}
