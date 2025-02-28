/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

import dev.redtronics.buildsrc.Project

plugins {
    `mokt-publishing`
    `mokt-multiplatform`
    `mokt-cinterop-generation`
    `mokt-build-constants`
}

repositories {
    mavenCentral()
    google()
}

group = Project.GROUP

kotlin {
    js(IR) {
        generateTypeScriptDefinitions()
        nodejs()
        useEsModules()
        binaries.library()
    }

//    val nativeDefFilePath = Path("../native-cinterop/cinterop.def")
//    linuxX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//
//    mingwX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//
//    macosX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    macosArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//
//    iosArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    iosX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    iosSimulatorArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//
//    watchosArm32 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    watchosArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    watchosX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    watchosSimulatorArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//
//    tvosArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    tvosX64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }
//    tvosSimulatorArm64 {
//        applyCInteropGeneration(nativeDefFilePath)
//    }

//    sourceSets {
//        commonMain {
//            dependencies {
//                api(libs.ktor.serialization.json)
//                api(libs.ktor.client.core)
//                api(libs.ktor.client.logging)
//                api(libs.ktor.client.content.negotiation)
//
//                api(libs.kotlin.reflect)
//                api(libs.kotlinx.serialization.json)
//                api(libs.kotlinx.datetime)
//            }
//        }
//    }

//    commonTest {
//        dependencies {
//            implementation(libs.kotest.assertions.core)
//            implementation(libs.kotest.framework.engine)
//            implementation(libs.kotest.property)
//        }
//    }

//    jvmMain {
//        dependencies {
//            api(libs.ktor.client.cio)
//            api(libs.logback.classic)
//        }
//    }
//
//    jvmTest {
//        dependencies {
//            implementation(libs.kotest.runner.junit5)
//        }
//    }
//
//    jsMain {
//        dependencies {
//            api(libs.ktor.client.js)
//            implementation(npm("open", "10.1.0"))
//        }
//    }
//
//    linuxMain {
//        dependencies {
//            api(libs.ktor.client.cio)
//        }
//    }
//
//    mingwMain {
//        dependencies {
//            api(libs.ktor.client.winhttp)
//        }
//    }
//
//    iosMain {
//        dependencies {
//            api(libs.ktor.client.darwin)
//        }
//    }
//
//    macosMain {
//        dependencies {
//            api(libs.ktor.client.darwin)
//        }
//    }
//
//    tvosMain {
//        dependencies {
//            api(libs.ktor.client.darwin)
//        }
//    }
//
//    watchosMain {
//        dependencies {
//            api(libs.ktor.client.darwin)
//        }
//    }
}
