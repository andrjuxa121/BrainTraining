package com.example.kmp_project.models

import org.jetbrains.compose.resources.DrawableResource

data class LevelInfo(
    val type: LevelType = LevelType.Colors,
    val name: String = "Скоро буде",
    val description: String = "Чекайте в наступному оновленні",
    val imgPreview: DrawableResource? = null,
    var bestScore: Int = 0
)

enum class LevelType {
    Colors, Directions
}