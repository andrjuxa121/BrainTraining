// ui/models/ArrowDirection.kt
package com.example.kmp_project.ui.models

enum class ArrowDirection(val text: String, val rotation: Float) {
    UP("вгору", 0f),
    RIGHT("вправо", 90f),
    DOWN("вниз", 180f),
    LEFT("вліво", 270f)
}