package com.tradingviewtask.build_logics

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.binaryen.BinaryenExec

class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val projectName = name

        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        extensions.configure<KotlinMultiplatformExtension> {

            applyDefaultHierarchyTemplate()

            androidTarget {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            jvmTarget.set(JvmTarget.JVM_17)
                        }
                    }
                }
            }

            // iOS
            iosArm64()
            iosSimulatorArm64()

            // Wasm
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                browser {
                    commonWebpackConfig {
                        outputFileName = "$projectName.js"
                    }
                }

                binaries.executable()
            }
        }

        extensions.configure<LibraryExtension> {
            compileSdk = 35
            defaultConfig {
                minSdk = 24
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        tasks.withType<BinaryenExec>().configureEach {
            binaryenArgs = mutableListOf(
                "--enable-gc",
                "--enable-reference-types",
                "--enable-exception-handling",
                "--enable-bulk-memory",
                "--enable-nontrapping-float-to-int",
                "-O0"
            )
        }
    }
}
