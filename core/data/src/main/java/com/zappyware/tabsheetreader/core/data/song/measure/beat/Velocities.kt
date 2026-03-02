package com.zappyware.tabsheetreader.core.data.song.measure.beat

object Velocities {
    const val MIN_VELOCITY = 15
    const val VELOCITY_INCREMENT = 16
    const val PIANO_PIANISSIMO = MIN_VELOCITY
    const val PIANISSIMO = MIN_VELOCITY + VELOCITY_INCREMENT
    const val PIANO = MIN_VELOCITY + VELOCITY_INCREMENT * 2
    const val MEZZO_PIANO = MIN_VELOCITY + VELOCITY_INCREMENT * 3
    const val MEZZO_FORTE = MIN_VELOCITY + VELOCITY_INCREMENT * 4
    const val FORTE = MIN_VELOCITY + VELOCITY_INCREMENT * 5
    const val FORTISSIMO = MIN_VELOCITY + VELOCITY_INCREMENT * 6
    const val FORTE_FORTISSIMO = MIN_VELOCITY + VELOCITY_INCREMENT * 7
    const val DEFAULT = FORTE
}
