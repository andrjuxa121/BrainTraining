package com.example.kmp_project.data

import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.models.LevelType
import testkmpproject.composeapp.generated.resources.Res
import testkmpproject.composeapp.generated.resources.colors
import testkmpproject.composeapp.generated.resources.directions

object GameLevels {
    val levels = mutableListOf(
        LevelInfo(
            LevelType.Colors,
            "Кольори",
            "Потрібно вказати чи колір зображеної фігури відповідає кольору, зазначеному" +
                    " у тексті запитання. Для відповіді використовуйте кнопоки \"Так\" або \"Ні\"," +
                    " розміщені під зображенням фігури.",
            Res.drawable.colors
        ),
        LevelInfo(
            LevelType.Directions,
            "Напрямки",
            "Потрібно вказати чи напрямок зображеної стрілки відповідає напрямку" +
                    " у тексті запитання. Для відповіді використовуйте кнопоки \"Так\" або \"Ні\"," +
                    " розміщені під зображенням стрілки.",
            Res.drawable.directions
        )
    ).apply {
        addAll(List(5) { LevelInfo() })
    }
}