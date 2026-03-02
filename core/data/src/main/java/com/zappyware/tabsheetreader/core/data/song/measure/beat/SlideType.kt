package com.zappyware.tabsheetreader.core.data.song.measure.beat

enum class SlideType(val value: Int) {
    IntoFromAbove(-2),
    IntoFromBelow(-1),
    None(0),
    ShiftSlideTo(1),
    LegatoSlideTo(2),
    OutDownwards(3),
    OutUpwards(4),
}
