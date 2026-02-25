package com.zappyware.tabsheetreader.core.data

data class MidiChannel(
    val channel: Int,
    val port: Int,
    val instrument: Int,
    val volume: Int,
    val balance: Int,
    val chorus: Int,
    val reverb: Int,
    val phaser: Int,
    val tremolo: Int,
)
