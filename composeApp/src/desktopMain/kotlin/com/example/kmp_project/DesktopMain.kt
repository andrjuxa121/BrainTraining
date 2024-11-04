package com.example.kmp_project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kmp_project.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TestKmpProject",
    ) {
        App()
    }
}