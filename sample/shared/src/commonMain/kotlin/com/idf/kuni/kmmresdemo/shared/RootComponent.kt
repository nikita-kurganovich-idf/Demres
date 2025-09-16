package com.idf.kuni.kmmresdemo.shared

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.idf.kuni.api.GuardComponent
import com.idf.kuni.api.PlatformComponent
import com.idf.kuni.api.WelcomeComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class Welcome(val component: WelcomeComponent) : Child
        data class Guard(val component: GuardComponent) : Child
        data class Platform(val component: PlatformComponent) : Child
    }
}