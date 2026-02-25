package com.zappyware.tabsheetreader.core.data

data class FileVersion(
    val version: String,
)

fun FileVersion.hasMasterEffect(): Boolean {
    val lastV = version.lastIndexOf('v')
    val versionNumber = version.substring(lastV + 1, version.length).toFloatOrNull()
    return versionNumber?.let { it > 5.0 } ?: false
}
