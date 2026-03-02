package com.zappyware.tabsheetreader.core.data.song

import com.zappyware.tabsheetreader.core.data.song.header.DirectionSign

data class Directions(
    val directions: Map<DirectionSign, Int>
)
