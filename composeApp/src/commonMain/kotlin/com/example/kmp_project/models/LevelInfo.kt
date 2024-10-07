package com.example.kmp_project.models

import com.example.kmp_project.data.LevelResults
import org.jetbrains.compose.resources.DrawableResource
import testkmpproject.composeapp.generated.resources.Res

data class LevelInfo(
    val type: LevelType = LevelType.Colors,
    val name: String = "Скоро буде",
    val description: String = "Чекайте в наступному оновленні",
    val imgPreview: DrawableResource? = null,
    var levelResults: LevelResults = LevelResults()
)

enum class LevelType {
    Colors, Directions
}