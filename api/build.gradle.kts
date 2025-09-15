plugins {
    alias(libs.plugins.convention.language)
    alias(libs.plugins.convention.publish)
}

android {
    namespace = "com.idf.kuni.api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.decompose)
            }
        }
    }
}