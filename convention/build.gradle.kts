plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.bundles.gradle.plugins)
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

gradlePlugin {
    plugins {
        register("convention.language") {
            id = libs.plugins.convention.language.get().pluginId
            implementationClass = "CommonLanguagePlugin"
        }
        register("convention.component") {
            id = libs.plugins.convention.component.get().pluginId
            implementationClass = "ComponentPlugin"
        }
        register("convention.ui") {
            id = libs.plugins.convention.ui.get().pluginId
            implementationClass = "CommonUiPlugin"
        }
        register("convention.base.compose") {
            id = libs.plugins.convention.base.compose.get().pluginId
            implementationClass = "BaseComposePlugin"
        }
        register("convention.publish") {
            id = libs.plugins.convention.publish.get().pluginId
            implementationClass = "PublishingPlugin"
        }
    }
}