// ui/menu/MenuScreen.kt
package com.example.kmp_project.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_project.models.LevelInfo
import com.example.kmp_project.style.TextStyles
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import testkmpproject.composeapp.generated.resources.Res
import testkmpproject.composeapp.generated.resources.compose_multiplatform

@Composable
fun MenuScreen(
    gameLevels: SnapshotStateList<LevelInfo>,
    selectedIndex: Int,
    onLevelSelected: (Int) -> Unit,
    onLevelStart: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LevelDetails(
            levelInfo = gameLevels[selectedIndex],
            onLevelStart = onLevelStart,
            isLevelAvailable = gameLevels[selectedIndex].imgPreview != null
        )
        LevelList(
            levels = gameLevels,
            onLevelSelected = onLevelSelected
        )
    }
}

@Composable
fun LevelDetails(
    levelInfo: LevelInfo,
    onLevelStart: () -> Unit,
    isLevelAvailable: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Найкращий результат", style = TextStyles.main)
        Text(levelInfo.bestScore.toString(), style = TextStyles.bold)

        levelInfo.imgPreview?.let { image ->
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 10.dp)
            )
        }

        Text(
            text = levelInfo.name,
            style = TextStyles.title,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = levelInfo.description,
            style = TextStyles.main,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        if (isLevelAvailable) {
            Button(onClick = onLevelStart, modifier = Modifier.padding(top = 16.dp)) {
                Text("Почати гру")
            }
        }
    }
}

@Composable
fun LevelList(
    levels: List<LevelInfo>,
    onLevelSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        itemsIndexed(levels) { index, level ->
            LevelPreview(
                levelInfo = level,
                onPreviewClicked = { onLevelSelected(index) }
            )
        }
    }
}

@Composable
fun LevelPreview(
    levelInfo: LevelInfo,
    onPreviewClicked: () -> Unit
) {
    val isLevelAvailable = levelInfo.imgPreview != null
    val modifier = Modifier
        .size(width = 150.dp, height = 100.dp)
        .padding(horizontal = 10.dp)
        .then(
            if (isLevelAvailable) {
                Modifier.clickable { onPreviewClicked() }
            } else {
                Modifier
            }
        )

    Surface(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            val imageResource = levelInfo.imgPreview ?: Res.drawable.compose_multiplatform
            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Text(
                text = levelInfo.name,
                style = TextStyles.main,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}