package com.idf.kuni.kmmresdemo.shared

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import kotlin.experimental.ExperimentalObjCName

@Suppress("Unused")
@OptIn(ExperimentalObjCName::class)
@ObjCName("rootComponent")
val iosRootComponent = DefaultRootComponent(
    context = DefaultComponentContext(
        lifecycle = ApplicationLifecycle(),
        stateKeeper = null,
        instanceKeeper = null,
        backHandler = null
    ),
    componentFactory = defaultComponentFactory
)