package com.example.kmp_project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.kmp_project.data.GameResult
import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.models.LevelType
import com.example.kmp_project.style.TextStyles
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

private const val QUESTIONS_SIZE = 4
private const val LEVEL_TIME_SECONDS = 5//30

private val randomQuestionIndex: Int
    get() = Random.nextInt(0, QUESTIONS_SIZE)

private fun randomResponseIndex(
    questionIndex: Int
): Int {
    return if (Random.nextBoolean()) {
        getOtherQuestionIndex(questionIndex)
    } else questionIndex
}

private fun getOtherQuestionIndex(
    questionIndex: Int
): Int {
    while (true) {
        val index = randomQuestionIndex
        if (index != questionIndex) {
             return index
        }
    }
}

@Composable
@Preview
fun Game(
    gameLevel: LevelInfo,
    onGameEnd: (result: GameResult) -> Unit,
    onBackToMenu: () -> Unit
) {
    var timeRemaining by rememberSaveable { mutableStateOf(LEVEL_TIME_SECONDS) }
    var score by rememberSaveable { mutableStateOf(0) }
    var scoreDelta by remember { mutableStateOf(0) }

    var correctAnswers by remember { mutableStateOf(0) }
    var wrongAnswers by remember { mutableStateOf(0) }

    var questionIndex by remember { mutableStateOf(randomQuestionIndex) }
    var responseIndex by remember { mutableStateOf(randomResponseIndex(questionIndex)) }
    var randomIndex by remember { mutableStateOf(randomQuestionIndex) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(1.seconds)
            timeRemaining--

            if (timeRemaining == 0) {
                val gameResult = GameResult(
                    score,
                    correctAnswers,
                    wrongAnswers
                )
                onGameEnd.invoke(gameResult)
                break
            }
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(0.1.seconds)

            if (scoreDelta != 0) {
                delay(0.1.seconds)
                scoreDelta = 0 // Then hide the text
            }
        }
    }
    val checkTheAnswer: (yes: Boolean) -> Unit = { yes ->
        val isAnswerCorrect = if (yes) {
            questionIndex == responseIndex
        } else questionIndex != responseIndex

        if (isAnswerCorrect) {
            correctAnswers++
            score++
        } else {
            wrongAnswers++
            score--
        }
        println("correctAnswers: $correctAnswers. wrongAnswers: $wrongAnswers")
        if (score < 0) score = 0

        scoreDelta = if (isAnswerCorrect) 1 else -1

        questionIndex = getOtherQuestionIndex(questionIndex)
        responseIndex = randomResponseIndex(questionIndex)
        randomIndex = randomQuestionIndex
    }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = timeRemaining / LEVEL_TIME_SECONDS.toFloat(),
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
                .height(10.dp).scale(scaleX = -1f, scaleY = 1f)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackToMenu) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        null,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = "Час до кінця",
                        style = TextStyles.main
                    )
                    Text(
                        text = "00:${timeRemaining}",
                        style = TextStyles.bold
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Рахунок",
                    style = TextStyles.bold
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = score.toString(),
                        style = TextStyles.score,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    if (scoreDelta != 0) {
                        Text(
                            text = if (scoreDelta > 0) "+1" else "-1",
                            style = TextStyles.score.copy(
                                color = if (scoreDelta > 0) {
                                    Color.Green
                                } else Color.Red,
                            ),
                            modifier = Modifier.padding(start = 50.dp, bottom = 10.dp)
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.fillMaxWidth().weight(1.5f)
        )
        LevelImage(
            levelType = gameLevel.type,
            questionIndex = questionIndex,
            shapeSize = 200,
            modifier = Modifier.fillMaxWidth().size(200.dp)
        )
        Spacer(
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Text(
            text = getLevelQuestion(
                gameLevel.type,
                randomIndex,
                responseIndex,
            ),
            style = TextStyles.title.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 30.dp)
        ) {
            val modifier = Modifier.weight(2f)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { checkTheAnswer(false) },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
            ) {
                Text(
                    text = "Ні",
                    style = TextStyles.title,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { checkTheAnswer(true) },
                colors = ButtonDefaults.buttonColors(Color.Green),
                modifier = modifier
            ) {
                Text(
                    text = "Так",
                    style = TextStyles.title,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun LevelImage(
    levelType: LevelType,
    questionIndex: Int,
    shapeSize: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (levelType == LevelType.Directions) {
            ArrowShape(
                size = shapeSize.toFloat(),
                color = Color.Green,
                direction = ArrowDirection.entries[questionIndex]
            )
        } else {
            PolygonShape(
                cornersCount = Random.nextInt(2, 6),
                size = shapeSize.toFloat(),
                color = Colors.entries[questionIndex].color
            )
        }
    }
}

private fun getLevelQuestion(
    levelType: LevelType,
    randomIndex: Int,
    responseIndex: Int
): AnnotatedString {
    return buildAnnotatedString {
        when (levelType) {
            LevelType.Colors -> {
                append("Колір фігури ")
                withStyle(style = SpanStyle(
                    color = Colors.entries[randomIndex].color,
                    fontWeight = FontWeight.ExtraBold
                )) {
                    append("${Colors.entries[responseIndex].text}?")
                }
            }
            LevelType.Directions -> {
                append("Стрілка показує ")
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.ExtraBold
                )) {
                    append("${ArrowDirection.entries[responseIndex].text}?")
                }
            }
        }
    }
}

//@Composable
//private fun CountdownTimer(
//    targetTime: Int,
//    content: @Composable (remainingTime: Int) -> Unit
//) {
//    var remainingTime by remember { mutableStateOf(targetTime) }
//
//    content.invoke(remainingTime)
//
//    var isRunning by remember { mutableStateOf(false) }
//    LifecycleResumeEffect(Unit) {
//        isRunning = true
//        onPauseOrDispose { isRunning = false }
//    }
//
//    LaunchedEffect(isRunning) {
//        while (isRunning) {
//            remainingTime--
//            delay(1000)
//        }
//    }
//}