package com.zappyware.tabsheetreader.core.data.song.measure.beat

class PitchClass {
    val just: Int
    val accidental: Int
    val value: Int
    val intonation: String

    constructor(semitone: Int, intonation: String? = null) {
        val v = semitone % 12
        this.value = v
        this.intonation = intonation ?: "flat"
        
        val name = NOTES[this.intonation]!![v]
        this.accidental = when {
            name.endsWith("b") -> -1
            name.endsWith("#") -> 1
            else -> 0
        }
        this.just = (v - this.accidental) % 12
    }

    constructor(tone: Int, accidental: Int, intonation: String? = null) {
        this.just = tone % 12
        this.accidental = accidental
        this.value = (this.just + accidental + 12) % 12
        this.intonation = intonation ?: if (accidental == -1) "flat" else "sharp"
    }

    constructor(name: String) {
        var v = NOTES["sharp"]!!.indexOf(name)
        if (v == -1) {
            v = NOTES["flat"]!!.indexOf(name)
        }
        require(v != -1) { "Invalid note name: $name" }
        
        this.value = v
        this.intonation = if (name.endsWith("b")) "flat" else "sharp"
        this.accidental = when {
            name.endsWith("b") -> -1
            name.endsWith("#") -> 1
            else -> 0
        }
        this.just = (v - this.accidental + 12) % 12
    }

    override fun toString(): String {
        return NOTES[intonation]!![value]
    }

    companion object {
        val NOTES = mapOf(
            "sharp" to listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"),
            "flat" to listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")
        )
    }
}
