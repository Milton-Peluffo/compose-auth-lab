package com.tomildev.trakii.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val info: Color,
    val habitSage: Color,
    val habitRose: Color,
    val habitMutedBlue: Color,
    val habitLavender: Color,
    val habitSand: Color,
    val habitSlate: Color,
)

val localExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("No ExtendedColors provided")
}