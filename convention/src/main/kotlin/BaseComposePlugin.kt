import extensions.ProjectPlugin
import extensions.projectDeps
import extensions.compose
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BaseComposePlugin: ProjectPlugin {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply(BaseLanguagePlugin::class)
                apply(projectDeps.plugins.composeCompiler.get().pluginId)
                apply(projectDeps.plugins.composeMultiplatform.get().pluginId)
            }
            with(extensions.getByType(KotlinMultiplatformExtension::class.java)) {
                with(sourceSets) {
                    androidMain.dependencies {
                        implementation(compose.preview)
                        implementation(compose.uiTooling)
                    }
                    commonMain.dependencies {
                        implementation(compose.ui)
                        implementation(compose.animation)
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                        implementation(compose.material3)
                        implementation(compose.components.uiToolingPreview)
                        implementation(compose.uiUtil)
                    }
                }
            }
        }
    }
}