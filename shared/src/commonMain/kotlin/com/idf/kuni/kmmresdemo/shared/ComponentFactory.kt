package com.idf.kuni.kmmresdemo.shared

import com.idf.kuni.api.GuardComponent
import com.idf.kuni.api.PlatformComponent
import com.idf.kuni.api.WelcomeComponent

interface ComponentFactory {
    fun PlatformComponent(onGoToBack: () -> Unit): PlatformComponent
    fun WelcomeComponent(onGoToNext: () -> Unit): WelcomeComponent
    fun GuardComponent(onGoToPlatform: () -> Unit): GuardComponent
}