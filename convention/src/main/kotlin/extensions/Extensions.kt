package extensions

import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.accessors.dm.LibrariesForLibsCore
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.gradle.kotlin.dsl.the
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private typealias AndroidExtensions = CommonExtension<
        out BuildFeatures,
        out BuildType,
        out DefaultConfig,
        out ProductFlavor,
        out AndroidResources,
        out Installation>

val Project.projectDeps: LibrariesForLibsCore
    get() = the<LibrariesForLibsCore>()

val Project.projectJavaVersion: JavaVersion
    get() = JavaVersion.toVersion(projectDeps.versions.java.get().toInt())


typealias ProjectPlugin = Plugin<Project>

fun Project.configureAndroid(
    commonExtension: AndroidExtensions,
    additionBlock: AndroidExtensions.() -> Unit = {}
) {
    commonExtension.apply {
        compileSdk = projectDeps.versions.android.compileSdk.get().toInt()
        defaultConfig {
            minSdk = projectDeps.versions.android.minSdk.get().toInt()
            compileSdk = projectDeps.versions.android.compileSdk.get().toInt()
        }
        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }
        additionBlock()
    }
}

internal val KotlinMultiplatformExtension.compose: ComposePlugin.Dependencies
    get() = (this as ExtensionAware).extensions.getByName("compose") as ComposePlugin.Dependencies


internal val Project.commonExtension: AndroidExtensions
    get() = (extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<ApplicationExtension>()
            ) as AndroidExtensions

internal val Project.modulePackage: String
    get() = "com.idf.kuni.${name.replace("-", ".")}"

internal val Project.moduleCamelName: String
    get() = project.name.replace(
        regex = Regex("-[a-z]"),
        transform = { result ->
            var transformedName = ""
            result.groups.forEach { group ->
                transformedName = group?.value?.drop(1)?.uppercase().orEmpty()
            }
            return@replace transformedName
        }
    ).uppercaseFirstChar()