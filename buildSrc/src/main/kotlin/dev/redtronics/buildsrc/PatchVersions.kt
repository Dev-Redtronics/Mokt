package dev.redtronics.buildsrc/*
 * MIT License
 * Copyright 2024 Nils Jäkel & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

import org.gradle.api.Project

/**
 * Patches the version in writerside.cfg and Cargo.toml
 *
 * @since 0.0.1
 * @author Nils Jäkel
 * */
internal fun Project.patchVersion() {
    val writersideCfg = rootProject.file("docs/writerside.cfg")
    val cargoToml = rootProject.file("mokt-rust-bindings/Cargo.toml")
    val files = listOf(writersideCfg, cargoToml)
    val toPatchedVersion = System.getenv(/* name = */ "CI_COMMIT_TAG") ?: "0.0.0"

    files.forEach { file ->
        val content = file.readText()
        val patchedContent = content.replace("0.0.0", toPatchedVersion)
        file.writeText(patchedContent)
    }
}
