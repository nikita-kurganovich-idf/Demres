
import extensions.ProjectPlugin
import extensions.projectDeps
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonLanguagePlugin : ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BaseLanguagePlugin::class)
                apply(AndroidSetupPlugin::class)
            }
            with(extensions.getByType(KotlinMultiplatformExtension::class.java)) {
                applyDefaultHierarchyTemplate()
                iosX64()
                iosArm64()
                iosSimulatorArm64()

                androidTarget()
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(projectDeps.napier)
                    }
                    commonTest.dependencies {
                        implementation(projectDeps.kotlin.test)
                    }
                }
            }
        }
    }
}