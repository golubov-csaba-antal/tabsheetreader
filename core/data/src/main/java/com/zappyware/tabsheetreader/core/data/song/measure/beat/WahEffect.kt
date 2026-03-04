package com.zappyware.tabsheetreader.core.data.song.measure.beat


data class WahEffect(
    val value: Int = -1,
    val display: Boolean = false
) {
    init {
        require(value in -2..100) { "value must be in range from -2 to 100, but was $value" }
    }

    fun isOff(): Boolean = value == OFF.value

    fun isNone(): Boolean = value == NONE.value

    fun isOn(): Boolean = value in 0..100

    companion object {
        val NONE = WahEffect(value = -2)
        val OFF = WahEffect(value = -1)
    }
}
