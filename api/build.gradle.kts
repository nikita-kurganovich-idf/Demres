plugins {
    alias(libs.plugins.convention.language)
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