package com.example.kmp_project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ResultPage(
    levelInfo: LevelInfo,
    gameResult: GameResult,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Рівень",
            style = TextStyles.main
        )
        Text(
            text = levelInfo.name,
            style = TextStyles.title
        )
        Image(
            imageResource(levelInfo.imgPreview!!), null,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 10.dp)
        )
        Row(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {}
            Column(
                modifier = Modifier.weight(2f).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ваш результат",
                    style = TextStyles.bold
                )
                Text(
                    text = gameResult.score.toString(),
                    style = TextStyles.score
                )
                AnswersCount(
                    gameResult,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.weight(1f)) {}
        }
        Button(
            onClick = onBackToMenu,
            modifier = Modifier.padding(10.dp)
        ) {
            Text("Повернутись в меню")
        }
    }
}

@Composable
private fun AnswersCount(
    gameResult: GameResult,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Правильних відповідей",
                style = TextStyles.main
            )
            Text(
                text = gameResult.correctAnswersCount.toString(),
                style = TextStyles.bold
            )
        }
        Column(
            modifier = Modifier.weight(1f).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Хибних відповідей",
                style = TextStyles.main
            )
            Text(
                text = gameResult.wrongAnswersCount.toString(),
                style = TextStyles.bold
            )
        }
    }
}