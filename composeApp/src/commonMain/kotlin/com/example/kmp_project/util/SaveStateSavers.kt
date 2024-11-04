// SaveStateSavers.kt
package com.example.kmp_project.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.kmp_project.data.GameResult

/**
 * Saver for GameResult.
 * Serializes GameResult to a List<Int> and restores it back.
 */
val GameResultSaver: Saver<GameResult, Any> = listSaver(
    save = { listOf(it.score, it.correctAnswersCount, it.wrongAnswersCount) },
    restore = { GameResult(it[0], it[1], it[2]) }
)