// ui/result/ResultScreen.kt
package com.example.kmp_project.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_project.data.GameResult
import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.style.TextStyles
import org.jetbrains.compose.resources.painterResource

@Composable
fun ResultScreen(
    levelInfo: LevelInfo,
    gameResult: GameResult,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Рівень", style = TextStyles.main)
        Text(levelInfo.name, style = TextStyles.title)

        levelInfo.imgPreview?.let { image ->
            Image(
                painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Ваш результат", style = TextStyles.bold)
        Text(gameResult.score.toString(), style = TextStyles.score)

        AnswersCount(gameResult)

        Button(
            onClick = onBackToMenu,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Повернутись в меню")
        }
    }
}

@Composable
private fun AnswersCount(gameResult: GameResult) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Правильних відповідей", style = TextStyles.main, textAlign = TextAlign.Center)
            Text(gameResult.correctAnswersCount.toString(), style = TextStyles.bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Хибних відповідей", style = TextStyles.main, textAlign = TextAlign.Center)
            Text(gameResult.wrongAnswersCount.toString(), style = TextStyles.bold)
        }
    }
}