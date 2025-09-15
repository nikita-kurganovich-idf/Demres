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

class PublishingPlugin : ProjectPlugin {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("maven-publish")
            }

            configure<PublishingExtension> {
                // Configure repositories
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

                target.extensions.findByType<KotlinMultiplatformExtension>()?.let { kotlinExt ->
                    val xcFramework = XCFramework()

                    kotlinExt.apply {
                        exportToIOS {
                            baseName = project.moduleCamelName
                            isStatic = true
                            xcFramework.add(this)
                        }

                        androidTarget {
                            publishLibraryVariants("release", "debug")
                        }
                    }

                    afterEvaluate {
                        publications {
                            withType<MavenPublication> {
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
            }

            tasks.register("generatePackageSwift") {
                group = "publishing"
                val outputDir = file("${layout.buildDirectory}/spm")
                outputs.dir(outputDir)

                doLast {
                    val packageSwift = file("$outputDir/Package.swift")
                    packageSwift.parentFile.mkdirs()

                    packageSwift.writeText(
                        """
                    // swift-tools-version:5.3
                    import PackageDescription
                    
                    let package = Package(
                        name: "${project.name}",
                        platforms: [
                            .iOS(.v13),
                        ],
                        products: [
                            .library(
                                name: "${project.name}",
                                targets: ["${project.name}"]
                            )
                        ],
                        targets: [
                            .binaryTarget(
                                name: "${project.name}",
                                path: "${project.name}.xcframework.zip"
                            )
                        ]
                    )
                    """.trimIndent()
                    )
                }
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