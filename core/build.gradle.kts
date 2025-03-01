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
}

group = Project.GROUP

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        generateTypeScriptDefinitions()
        nodejs()
        useEsModules()
        binaries.library()
    }

    linuxX64()
    mingwX64()

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
            }
        }
    }
}
