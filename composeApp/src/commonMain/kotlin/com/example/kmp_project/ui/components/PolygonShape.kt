// ui/components/PolygonShape.kt
package com.example.kmp_project.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PolygonShape(cornersCount: Int, size: Float, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(size.dp)) {
        val path = createPolygonPath(cornersCount, size / 2)
        drawPath(path, color)
    }
}

private fun createPolygonPath(corners: Int, radius: Float): Path {
    val path = Path()
    val angle = (2 * Math.PI / corners).toFloat()

    val centerX = radius
    val centerY = radius

    path.moveTo(
        centerX + radius * cos(0f),
        centerY + radius * sin(0f)
    )

    for (i in 1 until corners) {
        path.lineTo(
            centerX + radius * cos(angle * i),
            centerY + radius * sin(angle * i)
        )
    }
    path.close()
    return path
}