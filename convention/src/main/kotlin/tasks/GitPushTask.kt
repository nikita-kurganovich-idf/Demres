package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class GitPushTask : DefaultTask() {
    @get:Input
    abstract val versionTag: Property<String>

    @get:InputFile
    abstract val spmFile: RegularFileProperty

    @TaskAction
    fun push() {
        val versionTagValue = versionTag.get()
        val spmFileValue = spmFile.get().asFile

        project.exec { commandLine("git", "add", spmFileValue.absolutePath) }
        project.exec { commandLine("git", "commit", "-m", "Automatic SPM release for version $versionTagValue") }
        project.exec { commandLine("git", "tag", versionTagValue) }
        project.exec { commandLine("git", "push") }
        project.exec { commandLine("git", "push", "--tags") }

        println("Successfully pushed Package.swift and tag $versionTagValue to GitHub.")
    }
}