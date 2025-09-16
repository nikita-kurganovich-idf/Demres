package com.idf.kuni.kmmresdemo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.idf.kuni.kmmresdemo.shared.DefaultRootComponent
import com.idf.kuni.kmmresdemo.shared.defaultComponentFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val rootComponent = DefaultRootComponent(
            context = defaultComponentContext(),
            componentFactory = defaultComponentFactory
        )
        setContent {
            App(rootComponent)
        }
    }
}