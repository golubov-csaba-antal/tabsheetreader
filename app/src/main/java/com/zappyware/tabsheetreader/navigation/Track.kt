package com.zappyware.tabsheetreader.navigation

import androidx.navigation3.runtime.NavKey
import com.zappyware.tabsheetreader.core.data.TrackType
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val trackId: Long,
    val trackType: TrackType,
): NavKey
