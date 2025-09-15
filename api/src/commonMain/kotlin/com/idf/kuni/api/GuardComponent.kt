package com.idf.kuni.api

import com.arkivanov.decompose.value.Value

interface GuardComponent {

    val model: Value<Model>

    fun showImage()
    fun goToPlatform()
    data class Model(
        val isImageShown: Boolean
    )
}