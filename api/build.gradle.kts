plugins {
    alias(libsCore.plugins.convention.language)
    alias(libsCore.plugins.convention.publish)
}

android {
    namespace = "com.idf.kuni.api"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libsCore.decompose)
            }
        }
    }
}