plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libsCore.plugins.androidApplication) apply false
    alias(libsCore.plugins.androidLibrary) apply false
    alias(libsCore.plugins.composeMultiplatform) apply false
    alias(libsCore.plugins.composeCompiler) apply false
    alias(libsCore.plugins.kotlinMultiplatform) apply false
    alias(libsCore.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libsCore.plugins.androidLint) apply false
    alias(libsCore.plugins.kotlinx.serialization) apply false
    alias(libsCore.plugins.kotlinCocoapods) apply false
    alias(libsCore.plugins.moko.resources) apply false
}

allprojects {
    group = "com.idf.kuni"
    version = "0.0.14"
}

abstract class PublishAllGitHubReleasesTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {
    @get:Input
    abstract val releaseTag: Property<String>

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.ABSOLUTE)
    abstract val zipFiles: ListProperty<RegularFile>

    @TaskAction
    fun execute() {
        val tag = releaseTag.get()
        val filesToUpload = zipFiles.get().map { it.asFile.absolutePath }

        if (filesToUpload.any { it == null }) {
            logger.warn("Some XCFramework zip files were not found. Skipping those.")
        }

        val command = mutableListOf(
            "gh", "release", "create",
            tag,
            *filesToUpload.filterNotNull().toTypedArray(),
            "--title", "Release $tag",
            "--notes", "Automatic release for version $tag",
            "--target", "main"
        )

        logger.lifecycle("Executing command: ${command.joinToString(" ")}")

        execOperations.exec {
            commandLine(command)
        }
    }
}

tasks.register<PublishAllGitHubReleasesTask>("publishAllGitHubReleases") {
    group = "publishing"
    description = "Runs the 'prepareGitHubRelease' task on all modules that have it."

    val prepareTasks = subprojects.mapNotNull { it.tasks.findByName("prepareGitHubRelease") }
    dependsOn(prepareTasks)

    releaseTag.set(project.version.toString())
    val allZipFilesProvider = project.provider {
        subprojects.mapNotNull { subproject ->
            subproject.tasks.withType<Zip>().findByName("packageXCFramework")?.archiveFile?.get()
        }
    }
    zipFiles.set(allZipFilesProvider)
}