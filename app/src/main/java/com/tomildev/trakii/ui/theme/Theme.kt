package com.tomildev.trakii.ui.theme

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
    onPrimary = Black0,
    background = Black0,
    onBackground = White100,
    surface = Black30,
    onSurface = White100,
    surfaceVariant = Black30,
    onSurfaceVariant = White100,
    outline = Gray100,
    error = Error
)

private val LightColorScheme = lightColorScheme(
    primary = Black0,
    onPrimary = White100,
    background = White100,
    onBackground = Black0,
    surface = White70,
    onSurface = White100,
    surfaceVariant = White70,
    onSurfaceVariant = Black0,
    outline = Black60,
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
fun TrakiiTheme(
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
            shapes = Shapes,
            typography = Typography,
            content = content
        )
    }
}