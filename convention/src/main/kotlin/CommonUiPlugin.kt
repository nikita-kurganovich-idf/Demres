import dev.icerock.gradle.MultiplatformResourcesPluginExtension
import extensions.moduleCamelName
import extensions.modulePackage
import extensions.projectDeps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.File

class CommonUiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(CommonLanguagePlugin::class)
                apply(ComposeSetupPlugin::class)
                apply(BaseComposePlugin::class)
                apply(projectDeps.plugins.moko.resources.get().pluginId)
            }
            with(extensions.getByType(KotlinMultiplatformExtension::class.java)) {
                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(project(":api"))
                        implementation(projectDeps.decompose)
                        implementation(projectDeps.decompose.compose)
                        implementation(projectDeps.moko.resources)
                        implementation(projectDeps.moko.resources.compose)
                    }
                }
            }
            with(extensions.getByType<MultiplatformResourcesPluginExtension>()) {
                resourcesPackage.set(modulePackage)
                resourcesClassName.set("${moduleCamelName}R")

                with(resourcesSourceSets) {
                    getByName("commonMain").srcDirs(
                        File(projectDir, "src/commonMain/composeResources")
                    )
                    findByName("iosMain")?.srcDirs(
                        File(projectDir, "src/iosMain/composeResources")
                    )
                    findByName("androidMain")?.srcDirs(
                        File(projectDir, "src/androidMain/composeResources")
                    )
                }
            }
        }
    }
}