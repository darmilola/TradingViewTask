plugins {
    id("com.tradingviewtask.kmp.library")
    id("com.tradingviewtask.compose")
}

android {
    namespace = "com.tradingviewtask.shared.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)
        }
    }
}
