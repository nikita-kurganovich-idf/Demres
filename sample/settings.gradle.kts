rootProject.name = "res-sample"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven(url = "https://maven.pkg.github.com/nikita-kurganovich-idf/Demres"){
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_API_KEY")
            }
        }
    }
}

include(":composeApp")
include(":shared")
