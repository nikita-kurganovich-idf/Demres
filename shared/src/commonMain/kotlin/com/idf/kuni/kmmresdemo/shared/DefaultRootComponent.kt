package com.idf.kuni.kmmresdemo.shared

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.idf.kuni.kmmresdemo.shared.RootComponent.Child.Guard
import com.idf.kuni.kmmresdemo.shared.RootComponent.Child.Platform
import com.idf.kuni.kmmresdemo.shared.RootComponent.Child.Welcome
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val context: ComponentContext,
    private val componentFactory: ComponentFactory
) : RootComponent, ComponentContext by context {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Welcome,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createChild
    )


    private fun createChild(config: Config, context: ComponentContext): RootComponent.Child = with(componentFactory) {
        return when (config) {
            Config.Welcome -> {
                val component = WelcomeComponent(onGoToNext = ::goToGuard)
                Welcome(component)
            }

            Config.Guard -> {
                val component = GuardComponent(onGoToPlatform = ::goToPlatform)
                Guard(component)
            }

            Config.Platform -> {
                val component = PlatformComponent(onGoToBack = ::goToGuard)
                Platform(component)
            }
        }
    }

    private fun goToGuard() {
        navigation.replaceAll(Config.Guard)
    }

    private fun goToPlatform() {
        navigation.pushNew(Config.Platform)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Welcome : Config

        @Serializable
        data object Guard : Config

        @Serializable
        data object Platform : Config
    }
}
