package com.example.kmp_project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.kmp_project.data.GameLevels
import com.example.kmp_project.data.GameResult
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val gameLevels = rememberSaveable { GameLevels.levels }

    var activePage by rememberSaveable { mutableStateOf(Page.Menu) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var gameResult = rememberSaveable { GameResult() }

    MaterialTheme {
        when (activePage) {
            Page.Game -> Game(
                gameLevels[selectedIndex],
                onGameEnd = { result ->
                    gameResult = result
                    activePage = Page.Result
                },
                onBackToMenu = { activePage = Page.Menu}
            )
            Page.Result -> ResultPage(
                gameLevels[selectedIndex],
                gameResult,
                onBackToMenu = { activePage = Page.Menu}
            )
            else -> Menu(
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