plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("androidLibrary") {
            id = "com.tradingviewtask.android.library"
            implementationClass = "com.tradingviewtask.build_logics.AndroidLibraryConventionPlugin"
        }

        create("androidApplication") {
            id = "com.tradingviewtask.android.application"
            implementationClass = "com.tradingviewtask.build_logics.AndroidApplicationConventionPlugin"
        }

        create("kmpLibrary") {
            id = "com.tradingviewtask.kmp.library"
            implementationClass = "com.tradingviewtask.build_logics.KmpLibraryConventionPlugin"
        }

        create("compose") {
            id = "com.tradingviewtask.compose"
            implementationClass = "com.tradingviewtask.build_logics.ComposeConventionPlugin"
        }
    }
}
