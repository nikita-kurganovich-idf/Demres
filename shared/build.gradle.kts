import extensions.exportToIOS

plugins {
    alias(libs.plugins.convention.language)
    alias(libs.plugins.moko.resources)
}

kotlin {
    exportToIOS {
        baseName = "Shared"
        isStatic = true

        export(projects.api)
        export(projects.platform.ui)
        export(projects.guard.ui)
        export(projects.welcome.ui)

        export(libs.moko.resources)
        export(libs.decompose)
        export(libs.essenty.lifecycle)
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.api)
            api(projects.platform.ui)
            api(projects.guard.ui)
            api(projects.welcome.ui)

            implementation(projects.platform.businessLogic)
            implementation(projects.guard.businessLogic)
            implementation(projects.welcome.businessLogic)

            implementation(libs.decompose)
            implementation(libs.essenty.lifecycle)
        }
    }
}

android {
    namespace = "com.idf.kuni.kmmresdemo.shared"
}
