package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.security.MessageDigest

abstract class GeneratePackageSwiftTask : DefaultTask() {

    @get:Input
    abstract val projectName: Property<String>

    @get:Input
    abstract val xcframeworkUrl: Property<String>

    @get:OutputFile
    abstract val packageSwiftFile: RegularFileProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val xcframeworkZipFile: RegularFileProperty

    private fun calculateChecksum(file: File): String {
        val bytes = file.readBytes()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    @TaskAction
    fun generate() {
        val checksum = calculateChecksum(xcframeworkZipFile.get().asFile)

        val content = """
            // swift-tools-version:5.3
            import PackageDescription

            let package = Package(
                name: "${projectName.get()}",
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: "${projectName.get()}",
                        targets: ["${projectName.get()}"]
                    )
                ],
                targets: [
                    .binaryTarget(
                        name: "${projectName.get()}",
                        url: "${xcframeworkUrl.get()}",
                        checksum: "$checksum"
                    )
                ]
            )
        """.trimIndent()

        packageSwiftFile.get().asFile.writeText(content)
    }
}