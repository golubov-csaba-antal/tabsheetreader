package com.zappyware.tabsheetreader.core.data.song

data class MidiChannel(
    val channel: Int,
    val effectChannel: Int = 0,
    val port: Int = 0,
    val instrument: Int = 0,
    val volume: Int = 0,
    val balance: Int = 0,
    val chorus: Int = 0,
    val reverb: Int = 0,
    val phaser: Int = 0,
    val tremolo: Int = 0,
) {
    companion object {
        val Default = MidiChannel(0)
    }
}
