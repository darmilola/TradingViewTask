import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.tradingviewtask.kmp.library")
    id("com.tradingviewtask.compose")
    id("org.jetbrains.kotlin.native.cocoapods")
}

android {
    namespace = "com.tradingviewtask.app.compose"
}

kotlin {
    cocoapods {
        summary = "ComposeApp module for TradingViewTask"
        homepage = "https://github.com/tradingviewtask"
        version = "1.0.0"
        ios.deploymentTarget = "15.0"

        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)

            api(projects.shared.common)
            api(projects.shared.ui)

            implementation(libs.voyager.navigator)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            api(projects.apps.app.features.authentication)
            api(projects.apps.app.features.watchlists)
            api(projects.apps.app.features.chart)
            api(projects.apps.app.features.community)
            api(projects.apps.app.features.subscription)
            api(projects.apps.app.features.dashboard)
        }
    }
}

tasks.register("embedAndSignAppleFrameworkForXcode") {
    group = "Build"
    description = "Build and sign a framework for Xcode"
    val isDebug = System.getenv("CONFIGURATION")?.equals("Debug", ignoreCase = true) ?: true
    val sdk = System.getenv("SDK_NAME") ?: "iphonesimulator"
    dependsOn(if (sdk.startsWith("iphonesimulator")) {
        if (isDebug) "linkPodDebugFrameworkIosSimulatorArm64" else "linkPodReleaseFrameworkIosSimulatorArm64"
    } else {
        if (isDebug) "linkPodDebugFrameworkIosArm64" else "linkPodReleaseFrameworkIosArm64"
    })
}
