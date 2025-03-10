/*
 * MIT License
 * Copyright 2024 Nils Jäkel  & David Ernst
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software.
 */

import dev.redtronics.buildsrc.Project
import dev.redtronics.buildsrc.MoktExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year

plugins {
    org.jetbrains.kotlin.plugin.serialization
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.kotlinx.atomicfu
    io.kotest.multiplatform
    org.jetbrains.dokka
}

public val jvmTargetVersion: JvmTarget = JvmTarget.JVM_1_8
public val targetJavaVersion: JavaVersion = JavaVersion.VERSION_1_8

extensions.create(Project.NAME.lowercase(), MoktExtension::class)

kotlin {
    explicitApi()
    withSourcesJar()

    jvm {
        compilerOptions.jvmTarget = jvmTargetVersion
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        targetCompatibility = targetJavaVersion.toString()
        sourceCompatibility = targetJavaVersion.toString()
    }

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget = jvmTargetVersion
        }
    }

    withType<Test> {
        useJUnitPlatform()
        reports.junitXml.required = true
        systemProperty("gradle.build.dir", layout.buildDirectory.get().asFile.absolutePath)

        filter {
            isFailOnNoMatchingTests = false
        }

        testLogging {
            showExceptions = true
            showStandardStreams = true
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

public val projectModuleName: String = project.name.replaceFirstChar { it.uppercase() }
dokka {
    moduleName.set(projectModuleName)

    dokkaSourceSets.commonMain {
        includes.from("README.md")
    }

    pluginsConfiguration.html {
        footerMessage.set("Copyright © ${Project.INCEPTION_YEAR}-${Year.now().value} Nils Jäkel & David Ernst")
    }
}
