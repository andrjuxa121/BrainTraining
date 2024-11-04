// ui/theme/TextStyles.kt
package com.example.kmp_project.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyles {
    val title = TextStyle(
        color = Color.Black,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold
    )
    val score = TextStyle(
        color = Color.Black,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold
    )
    val bold = TextStyle(
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    val main = TextStyle(
        color = Color.Black,
        fontSize = 14.sp
    )
}