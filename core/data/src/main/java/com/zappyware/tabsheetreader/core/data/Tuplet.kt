package com.zappyware.tabsheetreader.core.data

import kotlin.math.roundToInt

data class Tuplet(
    val enters: Int,
    val times: Int
)

val supportedTuples = mapOf(
    1 to 1,
    3 to 2,
    5 to 4,
    6 to 4,
    7 to 4,
    9 to 8,
    10 to 8,
    11 to 8,
    12 to 8,
    13 to 8
)

fun Tuplet.convertTime(time: Float): Float {
    val fraction = Fraction((time * enters).roundToInt(), times)
    return if(fraction.denominator == 1) fraction.numerator.toFloat()
    else fraction.numerator / fraction.denominator.toFloat()
}

fun fromFraction(denominator: Int, numerator: Int) = Tuplet(denominator, numerator)

fun fromFraction(fraction: Fraction) = Tuplet(fraction.denominator, fraction.numerator)

fun Tuplet.isSupported(): Boolean =
    supportedTuples.containsKey(enters) && supportedTuples[enters] == times
