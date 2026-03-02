package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class Chord(
    val length: Int,
    val sharp: Boolean? = null,
    val root: PitchClass? = null,
    val type: ChordType? = null,
    val extension: ChordExtension? = null,
    val bass: PitchClass? = null,
    val tonality: ChordAlteration? = null,
    val add: Boolean? = null,
    val name: String = "",
    val fifth: ChordAlteration? = null,
    val ninth: ChordAlteration? = null,
    val eleventh: ChordAlteration? = null,
    val firstFret: Int? = null,
    val strings: List<Int> = List(length) { -1 },
    val barres: List<Barre> = emptyList(),
    val omissions: List<Boolean> = emptyList(),
    val fingerings: List<Fingering> = emptyList(),
    val show: Boolean? = null,
    val newFormat: Boolean? = null
) {
    val notes: List<Int>
        get() = strings.filter { it >= 0 }

    companion object {
        val None = Chord(length = 0)
    }
}