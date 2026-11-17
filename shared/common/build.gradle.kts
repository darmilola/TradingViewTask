plugins {
    id("com.tradingviewtask.kmp.library")
}

android {
    namespace = "com.tradingviewtask.shared.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
