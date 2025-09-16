import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.idf.kuni.guard.ui.GuardContent
import com.idf.kuni.kmmresdemo.shared.RootComponent
import com.idf.kuni.platform.ui.PlatformContent
import com.idf.kuni.welcome.ui.WelcomeContent

@Composable
fun App(rootComponent: RootComponent) {
    val stack by rootComponent.childStack.subscribeAsState()
    MaterialTheme {
        Scaffold { paddingValues ->
            Children(
                modifier = Modifier.padding(paddingValues),
                stack = stack
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Guard -> GuardContent(instance.component)
                    is RootComponent.Child.Platform -> PlatformContent(instance.component)
                    is RootComponent.Child.Welcome -> WelcomeContent(instance.component)
                }
            }
        }
    }
}