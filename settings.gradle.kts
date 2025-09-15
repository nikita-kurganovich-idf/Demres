rootProject.name = "idf-kmm-resdemo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("convention")

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
    }
}

include(":composeApp")
include(":shared")
include(":api")
include(":guard:ui")
include(":guard:business-logic")
include(":platform:ui")
include(":platform:business-logic")
include(":welcome:ui")
include(":welcome:business-logic")
