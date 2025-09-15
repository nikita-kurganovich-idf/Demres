import extensions.ProjectPlugin
import extensions.exportToIOS
import extensions.moduleCamelName
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import tasks.GeneratePackageSwiftTask

class PublishingPlugin : ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
            }

            configure<PublishingExtension> {
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

                val xcFramework = XCFramework()
                applyDefaultHierarchyTemplate()

                exportToIOS {
                    baseName = project.moduleCamelName
                    isStatic = true
                    xcFramework.add(this)
                }
                androidTarget {
                    publishLibraryVariants("release", "debug")
                }
            }
            configure<PublishingExtension> {
                publications {
                    withType<MavenPublication>().configureEach {
                        if (name == "kotlinMultiplatform") {
                            groupId = project.group.toString()
                            artifactId = project.name
                            version = project.version.toString()

                            pom {
                                name.set(project.name)
                                description.set("Kotlin Multiplatform library for sharing resources")
                                url.set("https://github.com/nikita-kurganovich-idf/Demres")

                                scm {
                                    connection.set("scm:git:github.com/nikita-kurganovich-idf/Demres.git")
                                    developerConnection.set("scm:git:ssh://github.com/nikita-kurganovich-idf/Demres.git")
                                    url.set("https://github.com/nikita-kurganovich-idf/Demres")
                                }
                            }
                        }
                    }
                }
            }

            tasks.register<GeneratePackageSwiftTask>("generatePackageSwift") {
                group = "publishing"
                projectName.set(project.name)
                outputDir.set(layout.buildDirectory.dir("spm"))
            }

            tasks.register<Zip>("packageXCFramework") {
                group = "publishing"
                dependsOn("assemble")

                val xcFrameworkDir = file("${layout.buildDirectory}/XCFrameworks/release")
                from(xcFrameworkDir) {
                    include("${project.name}.xcframework/**")
                }

                archiveBaseName.set(project.name)
                archiveExtension.set("xcframework.zip")
                destinationDirectory.set(file("${layout.buildDirectory}/libs"))
            }

            tasks.register("prepareGitHubRelease") {
                group = "publishing"
                dependsOn(
                    "publishAllPublicationsToGitHubPackagesRepository",
                    "packageXCFramework",
                    "generatePackageSwift"
                )

                doLast {
                    println("Release artifacts prepared:")
                    println("- Maven artifacts published to GitHub Packages")
                    println("- XCFramework package: ${project.layout.buildDirectory}/libs/${project.name}.xcframework.zip")
                    println("- SPM package manifest: ${project.layout.buildDirectory}/spm/Package.swift")
                }
            }
        }
    }
}