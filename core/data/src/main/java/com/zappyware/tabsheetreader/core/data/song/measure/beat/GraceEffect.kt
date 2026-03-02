package com.zappyware.tabsheetreader.core.data.song.measure.beat

data class GraceEffect(
    val duration: Int,
    val fret: Int,
    val isDead: Boolean = false,
    val isOnBeat: Boolean = false,
    val transition: Int,
    val velocity: Int = Velocities.DEFAULT,
)