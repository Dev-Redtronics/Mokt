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
    `mokt-core`
    `mokt-docs`
    `mokt-build-constants`
}

repositories {
    mavenCentral()
}

kotlin {
    linuxX64()

    macosX64()
    macosArm64()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()

    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":common"))

                // Mordant
                api(libs.mordant)

                // Ktor server
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.content.negotiation)
                implementation(libs.ktor.server.html)
            }
        }

        commonTest {
            dependencies {
                // Kotest
                implementation(libs.kotest.property)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.framework.engine)
            }
        }

        jvmTest {
            dependencies {
                // Kotest
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}

mokt {
    buildConstants {
        properties = mapOf(
            "MOKT_LOGO_URL" to "https://code.redtronics.dev/nils.jaekel/mokt/-/raw/master/assets/mokt_m_alpha.png?ref_type=heads",
            "MOKT_DEVICE_CODE_BACKGROUND" to "https://code.redtronics.dev/nils.jaekel/mokt/-/raw/master/assets/background.png?ref_type=heads"
        )
        onlyInternal = true
    }
}
