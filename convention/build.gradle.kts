plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libsCore.bundles.gradle.plugins)
    compileOnly(files(libsCore.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libsCore.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

gradlePlugin {
    plugins {
        register("convention.language") {
            id = libsCore.plugins.convention.language.get().pluginId
            implementationClass = "CommonLanguagePlugin"
        }
        register("convention.component") {
            id = libsCore.plugins.convention.component.get().pluginId
            implementationClass = "ComponentPlugin"
        }
        register("convention.ui") {
            id = libsCore.plugins.convention.ui.get().pluginId
            implementationClass = "CommonUiPlugin"
        }
        register("convention.base.compose") {
            id = libsCore.plugins.convention.base.compose.get().pluginId
            implementationClass = "BaseComposePlugin"
        }
        register("convention.publish") {
            id = libsCore.plugins.convention.publish.get().pluginId
            implementationClass = "PublishingPlugin"
        }
    }
}