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
