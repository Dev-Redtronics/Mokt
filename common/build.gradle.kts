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
import dev.redtronics.buildsrc.cinterop.applyNativeConfiguration

plugins {
    `mokt-core`
    `mokt-build-constants`
    `mokt-cinterop-generation`
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

    linuxX64 { applyNativeConfiguration() }
    mingwX64 { applyNativeConfiguration() }
    macosX64 { applyNativeConfiguration() }
    macosArm64 { applyNativeConfiguration() }
    iosArm64 { applyNativeConfiguration() }
    iosX64 { applyNativeConfiguration() }
    iosSimulatorArm64 { applyNativeConfiguration() }
    watchosArm32 { applyNativeConfiguration() }
    watchosArm64 { applyNativeConfiguration() }
    watchosX64 { applyNativeConfiguration() }
    watchosSimulatorArm64 { applyNativeConfiguration() }
    tvosArm64 { applyNativeConfiguration() }
    tvosX64 { applyNativeConfiguration() }
    tvosSimulatorArm64 { applyNativeConfiguration() }

    sourceSets {
        commonMain {
            dependencies {
                // Serialization
                api(libs.kotlinx.serialization.json)

                // Ktor Client
                api(libs.ktor.client.core)
                api(libs.ktor.client.logging)
                api(libs.ktor.client.content.negotiation)

                // Ktor Common
                api(libs.ktor.serialization.json)

                // Datetime
                api(libs.kotlinx.datetime)
            }
        }

        jvmMain {
            dependencies {
                // Ktor Client
                api(libs.ktor.client.cio)

                // Logging
                api(libs.logback)
            }
        }

        jsMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.js)
                implementation(npm("open", "10.1.0"))
            }
        }

        linuxMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.cio)
            }
        }

        mingwMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.winhttp)
            }
        }

        iosMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.darwin)
            }
        }

        macosMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.darwin)
            }
        }

        tvosMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.darwin)
            }
        }

        watchosMain {
            dependencies {
                // Ktor client
                api(libs.ktor.client.darwin)
            }
        }
    }
}
