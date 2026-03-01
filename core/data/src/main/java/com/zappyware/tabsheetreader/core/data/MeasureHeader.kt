package com.zappyware.tabsheetreader.core.data

data class MeasureHeader(
    val number: Int = 1,
    val start: Int = QUARTER_TIME,
    val hasDoubleBar: Boolean,
    val keySignature: KeySignatures = KeySignatures.CMajor,
    val timeSignature: TimeSignature,
    val timeSignatureChanged: Boolean = false,
    val marker: Marker? = null,
    val isRepeatOpen: Boolean,
    val repeatAlternatives: String?,
    val repeatClose: Int = -1,
    val tripletFeel: TripletFeel = TripletFeel.NONE,
    val directionSign: DirectionSign? = null,
    val fromDirectionSign: DirectionSign? = null,
    val beams: List<Int>,
)

val MeasureHeader.length: Float get() = timeSignature.numerator * timeSignature.denominator.time

val MeasureHeader.end: Float get() = start + length

val defaultBeams = listOf(2, 2, 2, 2)