package com.idf.kuni.platform.business_logic

import com.idf.kuni.api.PlatformComponent

class DefaultPlatformComponent(
    private val onGoToBack: () -> Unit
) : PlatformComponent {
    override val name: String = platform()

    override fun goBack() {
        onGoToBack()
    }
}