import dev.redtronics.buildsrc.Project

plugins {
    `mokt-core`
    `mokt-docs`
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
