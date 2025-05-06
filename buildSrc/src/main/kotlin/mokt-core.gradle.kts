import dev.redtronics.buildsrc.Project
import dev.redtronics.buildsrc.MoktExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    org.jetbrains.kotlin.plugin.serialization
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.kotlinx.atomicfu
    io.kotest.multiplatform
}

group = "${Project.GROUP}.${Project.NAME.lowercase()}"

public val jvmTargetVersion: JvmTarget = JvmTarget.JVM_21
public val targetJavaVersion: JavaVersion = JavaVersion.VERSION_21

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
