import extensions.ProjectPlugin
import extensions.moduleCamelName
import extensions.modulePackage
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import tasks.FinalizeReleaseTask
import tasks.GeneratePackageSwiftTask
import tasks.GitPushTask

class PublishingPlugin : ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
            }
            configure<PublishingExtension> {
                publications {
                    matching { it.name in listOf("iosArm64", "iosX64", "kotlinMultiplatform") }.all {
                        val targetPublication = this@all
                        tasks.withType<AbstractPublishToMaven>()
                            .matching { it.publication == targetPublication }
                            .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
                    }
                }
                repositories {
                    maven {
                        name = "GitHubPackages"
                        url = uri("https://maven.pkg.github.com/nikita-kurganovich-idf/Demres")
                        credentials {
                            username = System.getenv("GITHUB_USER")
                            password = System.getenv("GITHUB_API_KEY")
                        }
                    }
                }
            }
            extensions.findByType<KotlinMultiplatformExtension>()?.apply {
                androidTarget { publishLibraryVariants("release", "debug") }
                iosArm64()
                iosX64()
                iosSimulatorArm64()
                applyDefaultHierarchyTemplate()

                val xcFramework = XCFramework(moduleCamelName)
                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { target ->
                    target.binaries.framework {
                        baseName = moduleCamelName
                        isStatic = true
                        binaryOption("bundleId", modulePackage)
                        xcFramework.add(this)
                    }
                }
            }

            val packageXCFramework by tasks.register<Zip>("packageXCFramework") {
                group = "publishing"
                dependsOn("assemble", "assemble${moduleCamelName}XCFramework")

                archiveFileName.set("${moduleCamelName}.xcframework.zip")
                destinationDirectory.set(layout.buildDirectory.get().dir("libs"))

                from(layout.buildDirectory.dir("XCFrameworks/release"))
            }

            val generatePackageSwift by tasks.register<GeneratePackageSwiftTask>("generatePackageSwift") {
                group = "publishing"

                val zipFile = packageXCFramework.archiveFile
                val releaseTag = project.version.toString()
                val url =
                    "https://github.com/nikita-kurganovich-idf/Demres/releases/download/$releaseTag/${zipFile.get().asFile.name}"

                projectName.set(project.moduleCamelName)
                xcframeworkZipFile.set(zipFile)
                xcframeworkUrl.set(url)
                packageSwiftFile.set(layout.buildDirectory.get().file("spm/Package.swift"))
            }

            tasks.register<GitPushTask>("publishPackageSwift") {
                group = "publishing"
                dependsOn(generatePackageSwift)
                versionTag.set(project.version.toString())
                spmFile.set(layout.buildDirectory.get().file("spm/Package.swift"))
            }

            tasks.register<FinalizeReleaseTask>("prepareGitHubRelease") {
                group = "publishing"
                dependsOn(
                    "publishAllPublicationsToGitHubPackagesRepository",
                    generatePackageSwift
                )
                xcframeworkZipFile.set(packageXCFramework.archiveFile)
                spmPackageFile.set(generatePackageSwift.packageSwiftFile.get())
            }
        }
    }
}