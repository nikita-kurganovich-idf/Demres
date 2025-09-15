package com.idf.kuni.guard.business_logic

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.idf.kuni.api.GuardComponent
import com.idf.kuni.api.GuardComponent.Model

class DefaultGuardComponent(
    private val onGoToPlatform: () -> Unit,
) : GuardComponent {

    private val _model: MutableValue<Model> = MutableValue(Model(false))

    override val model: Value<Model> = _model

    override fun showImage() {
        _model.update {
            it.copy(isImageShown = !it.isImageShown)
        }
    }

    override fun goToPlatform() {
        onGoToPlatform()
    }
}