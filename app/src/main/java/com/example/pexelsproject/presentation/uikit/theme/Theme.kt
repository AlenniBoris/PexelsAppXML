package com.example.pexelsproject.presentation.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColorScheme(
    onPrimary = ThemeColors.Night.text,
    surface = ThemeColors.Night.surface,
    background = ThemeColors.Night.background
)

private val LightColorPalette = lightColorScheme(
    onPrimary = ThemeColors.Day.text,
    surface = ThemeColors.Day.surface,
    background = ThemeColors.Day.background
)

@Composable
fun PexelsApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}