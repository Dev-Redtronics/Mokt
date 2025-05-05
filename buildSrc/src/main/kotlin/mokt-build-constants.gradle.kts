import dev.redtronics.buildsrc.MoktExtension
import dev.redtronics.buildsrc.constants.BuildConstantsConfiguration
import dev.redtronics.buildsrc.constants.GenerateBuildConstants
import dev.redtronics.buildsrc.constants.buildConstantDir
import dev.redtronics.buildsrc.utils.executeTaskBeforeCompile

plugins {
    org.jetbrains.kotlin.multiplatform
}

public val moktExtension: MoktExtension = extensions.getByType<MoktExtension>()
public val buildConstantsConfiguration: BuildConstantsConfiguration = moktExtension.buildConstants

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
