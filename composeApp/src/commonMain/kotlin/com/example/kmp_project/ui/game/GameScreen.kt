// ui/game/GameScreen.kt
package com.example.kmp_project.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_project.data.GameResult
import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.models.LevelType
import com.example.kmp_project.style.TextStyles
import com.example.kmp_project.ui.components.ArrowShape
import com.example.kmp_project.ui.components.PolygonShape
import com.example.kmp_project.ui.models.ArrowDirection
import com.example.kmp_project.ui.models.Colors
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

private const val QUESTIONS_SIZE = 4
private const val LEVEL_TIME_SECONDS = 30

@Composable
fun GameScreen(
    gameLevel: LevelInfo,
    onGameEnd: (GameResult) -> Unit,
    onBackToMenu: () -> Unit
) {
    // State variables
    var timeRemaining by rememberSaveable { mutableStateOf(LEVEL_TIME_SECONDS) }
    var score by rememberSaveable { mutableStateOf(0) }
    var correctAnswers by rememberSaveable { mutableStateOf(0) }
    var wrongAnswers by rememberSaveable { mutableStateOf(0) }
    var questionIndex by remember { mutableStateOf(randomQuestionIndex) }
    var responseIndex by remember { mutableStateOf(randomResponseIndex(questionIndex)) }
    var randomIndex by remember { mutableStateOf(randomQuestionIndex) }
    var scoreDelta by remember { mutableStateOf(0) }

    // Timer effect
    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1.seconds)
            timeRemaining--
        }
        onGameEnd(GameResult(score, correctAnswers, wrongAnswers))
    }

    // Score Delta Reset
    LaunchedEffect(scoreDelta) {
        if (scoreDelta != 0) {
            delay(0.5.seconds)
            scoreDelta = 0
        }
    }

    // Check Answer Function
    val checkAnswer: (Boolean) -> Unit = { isYes ->
        val isCorrect = if (isYes) {
            questionIndex == responseIndex
        } else {
            questionIndex != responseIndex
        }

        if (isCorrect) {
            correctAnswers++
            score++
            scoreDelta = 1
        } else {
            wrongAnswers++
            score = max(0, score - 1)
            scoreDelta = -1
        }

        // Prepare next question
        questionIndex = getOtherQuestionIndex(questionIndex)
        responseIndex = randomResponseIndex(questionIndex)
        randomIndex = randomQuestionIndex
    }

    // UI Layout
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress Bar
        LinearProgressIndicator(
            progress = timeRemaining / LEVEL_TIME_SECONDS.toFloat(),
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
                .height(10.dp)
                .scale(scaleX = -1f, scaleY = 1f)
        )

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToMenu) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Час до кінця", style = TextStyles.main)
                Text("00:$timeRemaining", style = TextStyles.bold)
            }
        }

        // Score Display
        Text("Рахунок: $score", style = TextStyles.bold, modifier = Modifier.padding(top = 16.dp))

        // Score Delta
        if (scoreDelta != 0) {
            Text(
                text = if (scoreDelta > 0) "+1" else "-1",
                style = TextStyles.score.copy(color = if (scoreDelta > 0) Color.Green else Color.Red),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Level Image
        LevelImage(
            levelType = gameLevel.type,
            questionIndex = questionIndex,
            shapeSize = 200,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question Text
        Text(
            text = getLevelQuestion(gameLevel.type, randomIndex, responseIndex),
            style = TextStyles.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Answer Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { checkAnswer(false) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            ) {
                Text("Ні", style = TextStyles.title)
            }
            Button(
                onClick = { checkAnswer(true) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            ) {
                Text("Так", style = TextStyles.title)
            }
        }
    }
}

// Helper Functions
private val randomQuestionIndex: Int
    get() = Random.nextInt(0, QUESTIONS_SIZE)

private fun randomResponseIndex(questionIndex: Int): Int {
    return if (Random.nextBoolean()) {
        getOtherQuestionIndex(questionIndex)
    } else {
        questionIndex
    }
}

private fun getOtherQuestionIndex(questionIndex: Int): Int {
    var index: Int
    do {
        index = randomQuestionIndex
    } while (index == questionIndex)
    return index
}

@Composable
private fun LevelImage(
    levelType: LevelType,
    questionIndex: Int,
    shapeSize: Int,
    modifier: Modifier = Modifier
) {
    when (levelType) {
        LevelType.Directions -> {
            ArrowShape(
                size = shapeSize.toFloat(),
                color = Color.Green,
                direction = ArrowDirection.values()[questionIndex],
                modifier = modifier
            )
        }
        LevelType.Colors -> {
            PolygonShape(
                cornersCount = Random.nextInt(3, 7),
                size = shapeSize.toFloat(),
                color = Colors.values()[questionIndex].color,
                modifier = modifier
            )
        }
    }
}

private fun getLevelQuestion(
    levelType: LevelType,
    randomIndex: Int,
    responseIndex: Int
): String {
    return when (levelType) {
        LevelType.Colors -> {
            "Колір фігури ${Colors.values()[responseIndex].text}?"
        }
        LevelType.Directions -> {
            "Стрілка показує ${com.example.kmp_project.ui.models.ArrowDirection.values()[responseIndex].text}?"
        }
    }
}