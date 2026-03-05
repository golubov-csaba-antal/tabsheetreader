package com.zappyware.tabsheetreader.core.data.song

data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    companion object {
        val Red = Color(255, 0, 0)
    }
}
