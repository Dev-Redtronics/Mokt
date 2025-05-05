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

import dev.redtronics.buildsrc.cinterop.CompileRust
import dev.redtronics.buildsrc.utils.executeTasksBeforeCompile
import dev.redtronics.buildsrc.cinterop.GenerateCompilerDefinitionFiles

tasks {
    val nativeMoktDir = rootProject.file("mokt-native")
    val generateCompilerDefinitionFiles by register<GenerateCompilerDefinitionFiles>("generateCompilerDefinitionFiles") {

        val cinteropDir = rootProject.file("cinterop")
        if (!cinteropDir.exists()) {
            cinteropDir.mkdirs()
        }

        val defFile = cinteropDir.resolve("cinterop.def")
        if (!defFile.exists()) {
            defFile.createNewFile()
        }

        cinteropDefFile = defFile
        cinteropDirectory = cinteropDir
        nativeMoktDirectory = nativeMoktDir
    }

    val compileRust by register<CompileRust>("compileRust") {
        dependsOn(generateCompilerDefinitionFiles)
        nativeMoktDirectory = nativeMoktDir
    }
    project.executeTasksBeforeCompile(generateCompilerDefinitionFiles, compileRust)
}
