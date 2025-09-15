package com.idf.kuni.guard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.idf.kuni.api.GuardComponent
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GuardContent(
    component: GuardComponent
) {
    val model by component.model.subscribeAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = component::showImage) {
            Text(stringResource(GuardUiR.strings.guard_ui_show_image_button))
        }
        AnimatedVisibility(model.isImageShown) {
            Image(
                painter = painterResource(GuardUiR.images.chimera),
                contentDescription = null
            )
        }
        Button(onClick = component::goToPlatform) {
            Text(stringResource(GuardUiR.strings.guard_ui_next_button))
        }
    }
}