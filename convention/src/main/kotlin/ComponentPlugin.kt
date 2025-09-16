import extensions.ProjectPlugin
import extensions.projectDeps
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComponentPlugin : ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(CommonLanguagePlugin::class)
            }
            with(extensions.getByType(KotlinMultiplatformExtension::class.java)) {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(projectDeps.demres.api)

                        implementation(projectDeps.decompose)
                        implementation(projectDeps.essenty.lifecycle)
                    }
                }
            }
        }
    }
}