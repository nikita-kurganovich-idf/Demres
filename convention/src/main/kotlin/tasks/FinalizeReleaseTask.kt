package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class FinalizeReleaseTask : DefaultTask() {
    @get:InputFile
    abstract val xcframeworkZipFile: RegularFileProperty

    @get:InputFile
    abstract val spmPackageFile: RegularFileProperty

    @TaskAction
    fun printFinalOutput() {
        println("Release artifacts prepared:")
        println("- Maven artifacts published to GitHub Packages")
        println("- XCFramework package: ${xcframeworkZipFile.get().asFile.absolutePath}")
        println("- SPM package manifest: ${spmPackageFile.get().asFile.absolutePath}")
    }
}