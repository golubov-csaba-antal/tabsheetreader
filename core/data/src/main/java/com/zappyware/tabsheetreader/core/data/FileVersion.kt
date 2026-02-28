package com.zappyware.tabsheetreader.core.data

data class FileVersion(
    val version: String,
)

fun FileVersion.hasMasterEffect(): Boolean {
    val lastV = version.lastIndexOf('v')
    val versionNumber = version.substring(lastV + 1, version.length).toFloatOrNull()
    return versionNumber?.let { it > 5.0 } ?: false
}

fun FileVersion.isVersion5_0_0(): Boolean {
    val lastV = version.lastIndexOf('v')
    val versionNumber = version.substring(lastV + 1, version.length).toFloatOrNull()
    return versionNumber?.let { it == 5.0f } ?: false
}