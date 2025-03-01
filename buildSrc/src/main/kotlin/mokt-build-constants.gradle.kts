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

import dev.redtronics.buildsrc.MoktExtension
import dev.redtronics.buildsrc.constants.GenerateBuildConstants
import dev.redtronics.buildsrc.constants.buildConstantDir
import dev.redtronics.buildsrc.utils.executeTaskBeforeCompile

plugins {
    org.jetbrains.kotlin.multiplatform
}

val moktExtension = extensions.getByType<MoktExtension>()
val buildConstantsConfiguration = moktExtension.buildConstants

tasks {
    val generateBuildConstants by register<GenerateBuildConstants>("generateBuildConstants")

    afterEvaluate {
        val buildConstantsDir = buildConstantsConfiguration.buildConstantDir(project)
        if (!buildConstantsDir.exists()) {
            buildConstantsDir.mkdirs()
        }

        generateBuildConstants.apply {
            properties = buildConstantsConfiguration.properties
            buildConstantDirectory = buildConstantsDir
            onlyInternal = buildConstantsConfiguration.onlyInternal
            projectGroup = project.group.toString()
        }
    }
    project.executeTaskBeforeCompile(generateBuildConstants)
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(buildConstantsConfiguration.buildConstantDir(project))
        }
    }
}
