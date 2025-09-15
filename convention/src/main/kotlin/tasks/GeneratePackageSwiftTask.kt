package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GeneratePackageSwiftTask : DefaultTask() {
    @get:Input
    abstract val projectName: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val projectNameValue = projectName.get()

        val outputDirFile = outputDir.get().asFile
        outputDirFile.mkdirs()

        val packageSwift = File(outputDirFile, "Package.swift")
        packageSwift.createNewFile()
        packageSwift.writeText(
            """
        // swift-tools-version:5.3
        import PackageDescription
        
        let package = Package(
            name: "$projectNameValue",
            platforms: [
                .iOS(.v13),
            ],
            products: [
                .library(
                    name: "$projectNameValue",
                    targets: ["$projectNameValue"]
                )
            ],
            targets: [
                .binaryTarget(
                    name: "$projectNameValue",
                    path: "$projectNameValue.xcframework.zip"
                )
            ]
        )
        """.trimIndent()
        )
    }
}