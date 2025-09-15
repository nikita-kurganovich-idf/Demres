import extensions.ProjectPlugin
import extensions.commonExtension
import org.gradle.api.Project

class ComposeSetupPlugin: ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            commonExtension.apply {
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}