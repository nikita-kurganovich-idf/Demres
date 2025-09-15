import com.android.build.gradle.LibraryExtension
import extensions.ProjectPlugin
import extensions.configureAndroid
import extensions.projectDeps
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidSetupPlugin: ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(projectDeps.plugins.androidLibrary.get().pluginId)
            }
            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }
        }
    }
}