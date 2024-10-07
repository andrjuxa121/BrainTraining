package com.example.kmp_project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

enum class ArrowDirection(
    val text: String
) {
    UP("вгору"),
    DOWN("вниз"),
    LEFT("вліво"),
    RIGHT("вправо")
}

enum class Colors(
    val color: Color,
    val text: String
) {
    GREEN(Color.Green,"Зелений"),
    RED(Color.Red,"Червоний"),
    YELLOW(Color.Yellow,"Жовтий"),
    BLUE(Color.Blue,"Блакитний")
}

@Composable
fun ArrowShape(size: Float, color: Color, direction: ArrowDirection) {
    Canvas(modifier = Modifier.size(size.dp)) {
        // Calculate arrow path
        val path = createArrowPath(size)

        // Rotate the arrow based on the direction
        when (direction) {
            ArrowDirection.UP -> rotate(0f) { drawPath(path, color) }
            ArrowDirection.RIGHT -> rotate(90f) { drawPath(path, color) }
            ArrowDirection.DOWN -> rotate(180f) { drawPath(path, color) }
            ArrowDirection.LEFT -> rotate(270f) { drawPath(path, color) }
        }
    }
}

private fun DrawScope.createArrowPath(size: Float): Path {
    val path = Path()

    val arrowHeadSize = size / 3
    val arrowShaftWidth = size / 10
    val shaftLength = size - arrowHeadSize

    // Start from the middle of the canvas for symmetry
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

@Composable
fun PolygonShape(cornersCount: Int, size: Float, color: Color) {
    Canvas(modifier = Modifier.size(size.dp)) {
        if (cornersCount < 3) {
            drawCircle(color, size / 2.0f)
            return@Canvas
        }
        val path = createPolygonPath(cornersCount, size / 2)
        drawPath(path, color)
    }
}

fun DrawScope.createPolygonPath(
    corners: Int,
    radius: Float
): Path {
    val path = Path()
    val angle = (2 * Math.PI / corners).toFloat() // Angle between vertices

    // Calculate the first point of the polygon
    val centerX = size.width / 2
    val centerY = size.height / 2
    val startX = centerX + radius * cos(0f)
    val startY = centerY + radius * sin(0f)

    // Move to the first point
    path.moveTo(startX, startY)

    // Calculate and add the rest of the polygon's points
    for (i in 1 until corners) {
        val x = centerX + radius * cos(i * angle)
        val y = centerY + radius * sin(i * angle)
        path.lineTo(x, y)
    }

    // Close the path to complete the polygon
    path.close()

    return path
}
