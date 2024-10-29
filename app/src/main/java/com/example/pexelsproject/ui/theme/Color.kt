package com.example.pexelsproject.ui.theme

import androidx.compose.ui.graphics.Color


// define your colors for dark theme
val dark_btn = Color(android.graphics.Color.parseColor("#393939"))
val dark_bg = Color(android.graphics.Color.parseColor("#1E1E1E"))
// define your colors for light theme
val light_btn = Color(android.graphics.Color.parseColor("#F3F5F9"))
val light_bg = Color(android.graphics.Color.parseColor("#F6F8F9"))


sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val text: Color
)  {
    data object Night: ThemeColors(
        background = dark_bg,
        surface = dark_btn,
        text = Color.White
    )
    data object Day: ThemeColors(
        background = light_bg,
        surface = light_btn,
        text = Color.Black
    )
}