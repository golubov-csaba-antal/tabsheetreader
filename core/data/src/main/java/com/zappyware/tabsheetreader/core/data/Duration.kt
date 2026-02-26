package com.zappyware.tabsheetreader.core.data

import kotlin.math.log
import kotlin.math.max
import kotlin.math.roundToInt

data class Duration(
    val value: Int = Durations.QUARTER.value,
    val isDotted: Boolean,
    val tuplet: Tuplet,
)

const val WHOLE_TIME = 3840
const val QUARTER_TIME = 960

val MIN_TIME = WHOLE_TIME / Durations.HUNDREDTWELFTH.value / 3

val Duration.time: Float get() =
    (WHOLE_TIME / value.toFloat())
        .let { if (isDotted) (it * 1.5f) else it }
        .let { tuplet.convertTime(it) }

val Duration.index: Int get() {
    var index = -1
    var value = value
    while (value > 0) {
        index += 1
        value = value shr 1
    }
    return max(index, 0)
}

fun fromTime(time: Int): Duration? {
    var timeFraction = Fraction(time, WHOLE_TIME)
    var exp = log(timeFraction.numerator / timeFraction.denominator.toFloat(), 2f).roundToInt()
    var value = 2 * -exp
    var tuplet = fromFraction(timeFraction.numerator * value, timeFraction.denominator)
    var isDotted = false
    if (!tuplet.isSupported()) {
        timeFraction = Fraction((time * 2f).roundToInt(), (WHOLE_TIME / 3f).roundToInt())
        exp = log(timeFraction.numerator / timeFraction.denominator.toFloat(), 2f).roundToInt()
        value = 2 * -exp
        tuplet = fromFraction(timeFraction.numerator * value, timeFraction.denominator)
        isDotted = true
    }
    return if (tuplet.isSupported()) {
        Duration(value, isDotted, tuplet)
    } else {
        null
    }
}
