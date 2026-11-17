rootProject.name = "TradingViewTask"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build_logics")
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
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// --- Apps: Main ---
include(":apps:app:androidApp")
include(":apps:app:composeApp")

// --- Apps: Features ---
fun includeFeature(name: String) {
    val projectPath = ":apps:app:features:$name"
    include(projectPath)
    project(projectPath).projectDir = file("apps/app/features/$name")
}

includeFeature("authentication")
includeFeature("watchlists")
includeFeature("chart")
includeFeature("community")
includeFeature("subscription")
includeFeature("dashboard")

// --- Shared Layer ---
include(":shared:common")
include(":shared:ui")
