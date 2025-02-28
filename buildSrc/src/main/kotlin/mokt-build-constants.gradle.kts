import dev.redtronics.buildsrc.constants.BuildConstantsConfiguration
import dev.redtronics.buildsrc.constants.buildConstantDir
import dev.redtronics.buildsrc.constants.generateBuildConstants
import dev.redtronics.buildsrc.generateOnCompile
import dev.redtronics.buildsrc.onSyncExec

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

plugins {
    org.jetbrains.gradle.plugin.`idea-ext`
    org.jetbrains.kotlin.multiplatform
    idea
}

val buildConstants = extensions.create(
    "buildConstants",
    BuildConstantsConfiguration::class
)

kotlin {
    tasks {
        val name = rootProject.name
        val task = register("generateBuildConstants") {
            group = name
            description = "Generates build constants for all plugins."

            doLast {
                generateBuildConstants(project, buildConstants)
            }
        }
        generateOnCompile(project, task.get())
        onSyncExec(project, task.get(), rootProject.idea)
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(buildConstants.buildConstantDir(project))
        }
    }
}
