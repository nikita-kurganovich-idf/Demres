package com.idf.kuni.welcome.business_logic

import com.idf.kuni.api.WelcomeComponent

class DefaultWelcomeComponent(
    private val onGoToNext: () -> Unit
) : WelcomeComponent {
    override fun goNext() {
        onGoToNext()
    }
}