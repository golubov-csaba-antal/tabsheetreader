package com.zappyware.tabsheetreader

import com.zappyware.tabsheetreader.core.data.song.Durations

// Musical notes: https://www.unicode.org/charts/PDF/U1D100.pdf
object MusicalCharacters {

    fun getNotationCharacter(durationValue: Int): String =
        when(durationValue) {
            Durations.WHOLE.value -> String(Character.toChars(0x1D15D))
            Durations.HALF.value -> String(Character.toChars(0x1D15E))
            Durations.QUARTER.value -> String(Character.toChars(0x1D15F))
            Durations.EIGHTH.value -> String(Character.toChars(0x1D160))
            Durations.SIXTEENTH.value -> String(Character.toChars(0x1D161))
            Durations.THIRTYSECOND.value -> String(Character.toChars(0x1D162))
            Durations.SIXTYFOURTH.value -> String(Character.toChars(0x1D163))
            Durations.HUNDREDTWENTYEIGHTH.value -> String(Character.toChars(0x1D164))
            else -> String(Character.toChars(0x1D13D)) // Default to quarter rest
        }

    fun getRestCharacter(durationValue: Int): String =
        when(durationValue) {
            Durations.WHOLE.value -> String(Character.toChars(0x1D13B))
            Durations.HALF.value -> String(Character.toChars(0x1D13C))
            Durations.QUARTER.value -> String(Character.toChars(0x1D13D))
            Durations.EIGHTH.value -> String(Character.toChars(0x1D13E))
            Durations.SIXTEENTH.value -> String(Character.toChars(0x1D13F))
            Durations.THIRTYSECOND.value -> String(Character.toChars(0x1D140))
            Durations.SIXTYFOURTH.value -> String(Character.toChars(0x1D141))
            Durations.HUNDREDTWENTYEIGHTH.value -> String(Character.toChars(0x1D142))
            else -> String(Character.toChars(0x1D13D)) // Default to quarter rest
        }

    fun getDeadNoteCharacter() = "X"

    fun getIntonationCharacter(intonation: String?): String =
        when(intonation) {
            "flat" -> String(Character.toChars(0x266D))
            "natural" -> String(Character.toChars(0x266E))
            "sharp" -> String(Character.toChars(0x266F))
            else -> "" // default to nothing
        }
}
