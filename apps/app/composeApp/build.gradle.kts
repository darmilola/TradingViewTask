plugins {
    id("com.tradingviewtask.kmp.library")
    id("com.tradingviewtask.compose")
}

android {
    namespace = "com.tradingviewtask.app.compose"
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
