import extensions.ProjectPlugin
import extensions.projectDeps
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BaseLanguagePlugin: ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(projectDeps.plugins.kotlinMultiplatform.get().pluginId)
                apply(projectDeps.plugins.kotlinx.serialization.get().pluginId)
            }

            with(extensions.getByType(KotlinMultiplatformExtension::class.java)) {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                    freeCompilerArgs.add("-Xcontext-parameters")

                    optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
                    optIn.add("kotlin.experimental.ExperimentalObjCRefinement")
                }
            }
        }
    }
}