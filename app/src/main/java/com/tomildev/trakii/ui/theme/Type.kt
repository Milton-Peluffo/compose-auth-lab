package com.tomildev.trakii.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 43.sp,
        lineHeight = 45.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Light
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal
    )
)