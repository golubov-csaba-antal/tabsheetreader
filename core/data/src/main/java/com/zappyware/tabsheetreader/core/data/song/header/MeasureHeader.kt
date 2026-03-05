package com.zappyware.tabsheetreader.core.data.song.header

data class MeasureHeader(
    val number: Int = 1,
    val start: Int = QUARTER_TIME,
    val hasDoubleBar: Boolean = false,
    val keySignature: KeySignatures = KeySignatures.CMajor,
    val timeSignature: TimeSignature = TimeSignature.Default,
    val timeSignatureChanged: Boolean = false,
    val marker: Marker? = null,
    val isRepeatOpen: Boolean = false,
    val repeatAlternatives: String? = null,
    val repeatClose: Int = -1,
    val tripletFeel: TripletFeel = TripletFeel.NONE,
    val directionSign: DirectionSign? = null,
    val fromDirectionSign: DirectionSign? = null,
    val beams: List<Int> = emptyList(),
)

val MeasureHeader.length: Int get() = timeSignature.numerator * timeSignature.denominator.time

val MeasureHeader.end: Int get() = start + length

val defaultBeams = listOf(2, 2, 2, 2)
