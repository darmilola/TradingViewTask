plugins {
    id("com.tradingviewtask.kmp.library")
    id("com.tradingviewtask.compose")
}

android {
    namespace = "com.tradingviewtask.app.features.dashboard"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)

            api(projects.shared.common)
            api(projects.shared.ui)
            api(projects.apps.app.features.watchlists)
            api(projects.apps.app.features.chart)
            api(projects.apps.app.features.community)
            api(projects.apps.app.features.subscription)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
        }
    }
}
