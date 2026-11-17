plugins {
    id("com.tradingviewtask.kmp.library")
    id("com.tradingviewtask.compose")
}

android {
    namespace = "com.tradingviewtask.app.features.community"
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
            implementation(libs.voyager.screenModel)
        }
    }
}
