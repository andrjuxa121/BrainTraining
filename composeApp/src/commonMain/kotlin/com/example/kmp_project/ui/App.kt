package com.example.kmp_project.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.example.kmp_project.data.GameLevels
import com.example.kmp_project.data.GameResult
import com.example.kmp_project.ui.game.GameScreen
import com.example.kmp_project.ui.menu.MenuScreen
import com.example.kmp_project.ui.result.ResultScreen
import com.example.kmp_project.util.GameResultSaver
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
@Preview
fun App() {
    val gameLevels = remember { GameLevels.levels.toMutableStateList() }

    var activePage by rememberSaveable { mutableStateOf(Page.Menu) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var gameResult by rememberSaveable(stateSaver = GameResultSaver) { mutableStateOf(GameResult()) }

    MaterialTheme {
        when (activePage) {
            Page.Game -> GameScreen(
                gameLevel = gameLevels[selectedIndex],
                onGameEnd = { result ->
                    // Update best score
                    val currentLevel = gameLevels[selectedIndex]
                    val updatedLevel = currentLevel.copy(
                        bestScore = max(result.score, currentLevel.bestScore)
                    )
                    gameLevels[selectedIndex] = updatedLevel

                    gameResult = result
                    activePage = Page.Result
                },
                onBackToMenu = { activePage = Page.Menu }
            )
            Page.Result -> ResultScreen(
                levelInfo = gameLevels[selectedIndex],
                gameResult = gameResult,
                onBackToMenu = { activePage = Page.Menu }
            )
            Page.Menu -> MenuScreen(
                gameLevels = gameLevels,
                selectedIndex = selectedIndex,
                onLevelSelected = { levelIndex ->
                    selectedIndex = levelIndex
                },
                onLevelStart = { activePage = Page.Game }
            )
        }
    }
}

private enum class Page {
    Menu, Game, Result
}