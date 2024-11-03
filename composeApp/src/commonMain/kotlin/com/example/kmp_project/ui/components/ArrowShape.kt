// ui/components/ArrowShape.kt
package com.example.kmp_project.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.kmp_project.ui.models.ArrowDirection

@Composable
fun ArrowShape(size: Float, color: Color, direction: ArrowDirection, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(size.dp)) {
        val path = createArrowPath(size)
        rotate(degrees = direction.rotation) {
            drawPath(path, color)
        }
    }
}

private fun createArrowPath(size: Float): Path {
    val path = Path()

    val arrowHeadSize = size / 3
    val arrowShaftWidth = size / 10
    val shaftLength = size - arrowHeadSize

    val centerX = size / 2
    val centerY = size / 2

    // Arrow shaft
    path.moveTo(centerX - arrowShaftWidth / 2, centerY + shaftLength / 2)
    path.lineTo(centerX + arrowShaftWidth / 2, centerY + shaftLength / 2)
    path.lineTo(centerX + arrowShaftWidth / 2, centerY - shaftLength / 2)
    path.lineTo(centerX - arrowShaftWidth / 2, centerY - shaftLength / 2)
    path.close()

    // Arrow head
    path.moveTo(centerX - arrowHeadSize / 2, centerY - shaftLength / 2)
    path.lineTo(centerX + arrowHeadSize / 2, centerY - shaftLength / 2)
    path.lineTo(centerX, centerY - shaftLength / 2 - arrowHeadSize)
    path.close()

    return path
}