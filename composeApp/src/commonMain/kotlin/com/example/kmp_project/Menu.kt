package com.example.kmp_project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.style.TextStyles
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import testkmpproject.composeapp.generated.resources.Res
import testkmpproject.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun Menu(
    gameLevels: List<LevelInfo>,
    selectedIndex: Int,
    onLevelSelected: (levelIndex: Int) -> Unit,
    onLevelStart: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LevelDetails(
            levelInfo = gameLevels[selectedIndex],
            modifier = Modifier.fillMaxWidth().weight(1f),
            onLevelStart = onLevelStart
        )
        ListOfLevels(
            levels = gameLevels,
            onLevelSelected = onLevelSelected
        )
    }
}

@Composable
fun LevelDetails(
    levelInfo: LevelInfo,
    modifier: Modifier,
    onLevelStart: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Найкращий результат",
            style = TextStyles.main
        )
        Text(
            text = levelInfo.bestScore.toString(),
            style = TextStyles.bold
        )
        Image(
            imageResource(levelInfo.imgPreview!!), null,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 10.dp)
        )
        Row {
            Column(modifier = Modifier.weight(1f)) {}
            Column(
                modifier = Modifier.weight(2f).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = levelInfo.name,
                    style = TextStyles.title
                )
                Text(
                    text = levelInfo.description,
                    style = TextStyles.main,
                    textAlign = TextAlign.Center,
                )
            }
            Column(modifier = Modifier.weight(1f)) {}
        }
        Button(onClick = onLevelStart) {
            Text("Почати гру")
        }
    }
}

@Composable
fun ListOfLevels(
    levels: List<LevelInfo>,
    onLevelSelected: (index: Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            ).padding(vertical = 10.dp)
    ) {
        itemsIndexed(items = levels) { index, level ->
            LevelPreview(
                levelInfo = level,
                onPreviewClicked = {
                    onLevelSelected.invoke(index)
                }
            )
        }

    }
}

@Composable
fun LevelPreview(
    levelInfo: LevelInfo,
    onPreviewClicked: () -> Unit
) {
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(150.dp, 100.dp)
            .padding(horizontal = 10.dp)
            .clickable {
                if (levelInfo.imgPreview != null) {
                    onPreviewClicked.invoke()
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            val imageRes = levelInfo.imgPreview ?: Res.drawable.compose_multiplatform
            Image(
                painterResource(imageRes), null,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Text(
                text = levelInfo.name,
                style = TextStyles.main,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}