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

        // Node.js
        ivy {
            url = uri("https://nodejs.org/dist")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision]-[classifier].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("org.nodejs")
            }
        }

        // Yarn
        ivy {
            url = uri("https://github.com/yarnpkg/yarn/releases/download")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("com.yarnpkg")
            }
        }

        // Binaryen
        ivy {
            url = uri("https://github.com/WebAssembly/binaryen/releases/download")
            patternLayout {
                artifact("version_[revision]/[artifact]-version_[revision]-[classifier].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("com.github.webassembly")
            }
        }
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

        // Repository for Node.js binaries
        ivy {
            url = uri("https://nodejs.org/dist")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision]-[classifier].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("org.nodejs")
            }
        }

        // Repository for Yarn binaries
        ivy {
            url = uri("https://github.com/yarnpkg/yarn/releases/download")
            patternLayout {
                artifact("v[revision]/[artifact]-v[revision].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("com.yarnpkg")
            }
        }

        // Repository for Binaryen binaries
        ivy {
            url = uri("https://github.com/WebAssembly/binaryen/releases/download")
            patternLayout {
                artifact("version_[revision]/[artifact]-version_[revision]-[classifier].[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("com.github.webassembly")
            }
        }
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
