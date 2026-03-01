package com.zappyware.tabsheetreader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass

object MeasureTypography {

    fun getTypography(windowSizeClass: WindowSizeClass): Typography {
        val hasLargeScreen = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
        return if (hasLargeScreen) {
            largeDeviceTypography
        } else {
            mediumDeviceTypography
        }
    }

    private val mediumDeviceTypography = Typography(
        headlineSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 9.sp,
            lineHeight = 11.sp,
            letterSpacing = 0.sp
        ),
        displayMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
            lineHeight = 8.sp,
            letterSpacing = 0.sp
        ),
    )

    private val largeDeviceTypography = Typography(
        headlineSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            letterSpacing = 0.sp
        ),
        displayMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 60.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp
        ),
    )
}
