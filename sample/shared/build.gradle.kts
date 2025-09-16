plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(libs.api)
            export(libs.guard.ui)
            export(libs.platform.ui)
            export(libs.welcome.ui)

            export(libs.moko.resources)
            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.api)
            api(libs.guard.ui)
            api(libs.platform.ui)
            api(libs.welcome.ui)

            api(libs.guard.business.logic)
            api(libs.platform.business.logic)
            api(libs.welcome.business.logic)

            implementation(libs.decompose)
            implementation(libs.essenty.lifecycle)
        }
    }
}

android {
    namespace = "com.idf.kuni.kmmresdemo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
