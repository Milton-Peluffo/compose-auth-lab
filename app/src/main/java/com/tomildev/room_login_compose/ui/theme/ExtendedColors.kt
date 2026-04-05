package com.tomildev.room_login_compose.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A data class that defines a set of custom semantic colors extending the standard Material Design
 * color palette for the application theme.
 *
 * @property success The color used to indicate a successful operation or state.
 * @property warning The color used to indicate a warning or a state that requires caution.
 * @property info The color used to indicate neutral informational messages or states.
 */
data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val info: Color,
)

val localExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        success = Color.Unspecified,
        warning = Color.Unspecified,
        info = Color.Unspecified,
    )
}