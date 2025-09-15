package com.idf.kuni.platform.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.idf.kuni.api.PlatformComponent
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PlatformContent(
    component: PlatformComponent
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(text = stringResource(PlatformUiR.strings.platform_ui_hello, component.name))
        Image(
            painter = painterResource(platformImageRes),
            contentDescription = null
        )
        Button(onClick = component::goBack) {
            Text(text = stringResource(PlatformUiR.strings.platform_ui_back_button))
        }
    }
}

expect val platformImageRes: ImageResource