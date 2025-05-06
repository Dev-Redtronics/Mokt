plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "Mokt"

include("common", "core", "authentication", "example", "launcher", "inspect")
