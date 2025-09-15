package com.idf.kuni.kmmresdemo.shared

import com.idf.kuni.api.GuardComponent
import com.idf.kuni.api.PlatformComponent
import com.idf.kuni.api.WelcomeComponent
import com.idf.kuni.guard.business_logic.DefaultGuardComponent
import com.idf.kuni.platform.business_logic.DefaultPlatformComponent
import com.idf.kuni.welcome.business_logic.DefaultWelcomeComponent

val defaultComponentFactory = object : ComponentFactory {
    override fun PlatformComponent(onGoToBack: () -> Unit): PlatformComponent = DefaultPlatformComponent(
        onGoToBack = onGoToBack
    )

    override fun WelcomeComponent(onGoToNext: () -> Unit): WelcomeComponent = DefaultWelcomeComponent(
        onGoToNext = onGoToNext
    )

    override fun GuardComponent(onGoToPlatform: () -> Unit): GuardComponent = DefaultGuardComponent(
        onGoToPlatform = onGoToPlatform
    )
}