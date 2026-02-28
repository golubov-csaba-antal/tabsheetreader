package com.zappyware.tabsheetreader.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val trackIndex: Int,
    val name: String,
): NavKey
