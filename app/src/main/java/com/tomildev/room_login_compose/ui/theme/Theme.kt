package com.tomildev.room_login_compose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = White100,
    onPrimary = Dark,
    background = Dark,
    onBackground = White100,
    surface = Gray20,
    surfaceVariant = Gray20,
    onSurfaceVariant = White100,
    outline = Gray80,
    error = Error
)

private val LightColorScheme = lightColorScheme(
    primary = Dark,
    onPrimary = White100,
    background = White80,
    onBackground = Dark,
    surface = White100,
    surfaceVariant = White100,
    onSurfaceVariant = Dark,
    outline = Gray30,
    error = Error
)

private val ExtendedLightColors = ExtendedColors(
    success = SuccessGreen,
    warning = WarningOrange,
    info = InfoBlue,
)

private val ExtendedDarkColors = ExtendedColors(
    success = SuccessGreen,
    warning = WarningOrange,
    info = InfoBlue,
)

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = localExtendedColors.current
}

@Composable
fun Room_login_composeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val extendedColors = if (darkTheme) ExtendedDarkColors else ExtendedLightColors

    CompositionLocalProvider(
        localExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}