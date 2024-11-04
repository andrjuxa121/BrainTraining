package com.example.kmp_project.data

import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.models.LevelType
import testkmpproject.composeapp.generated.resources.Res
import testkmpproject.composeapp.generated.resources.colors
import testkmpproject.composeapp.generated.resources.directions

object GameLevels {
    val levels: List<LevelInfo> = listOf(
        // Existing levels
        LevelInfo(
            type = LevelType.Colors,
            name = "Кольори",
            description = "Потрібно вказати чи колір зображеної фігури відповідає кольору, зазначеному" +
                    " у тексті запитання. Для відповіді використовуйте кнопоки \"Так\" або \"Ні\"," +
                    " розміщені під зображенням фігури.",
            imgPreview = Res.drawable.colors
        ),
        LevelInfo(
            type = LevelType.Directions,
            name = "Напрямки",
            description = "Потрібно вказати чи напрямок зображеної стрілки відповідає напрямку" +
                    " у тексті запитання. Для відповіді використовуйте кнопоки \"Так\" або \"Ні\"," +
                    " розміщені під зображенням стрілки.",
            imgPreview = Res.drawable.directions
        )
        // Placeholder levels
    ) + List(5) { LevelInfo() }
}