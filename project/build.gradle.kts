import org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil
import java.util.Calendar
import java.util.jar.Attributes

plugins {
    application // Adds support for building a CLI application.

    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.release.axion)

    alias(libs.plugins.shadow)

    jacoco

    `maven-publish`

    alias(libs.plugins.dokka)
}

scmVersion {
    tag {
        prefix = ""
    }
}

version = scmVersion.version

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    implementation(libs.utility)

    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jetbrains)

    testImplementation(libs.kotlin.mock)
}

// Apply a specific Java toolchain to ease working on different environments.
kotlin {
    jvmToolchain((property("java.version") as String).toInt())
}

java {
    withSourcesJar()
}

application {
    mainClass = "${group}.${property("package")}.MainKt"
}

tasks {
    withType<Jar> {
        archiveBaseName = rootProject.name
    }

    jar {
        manifest {
            attributes[Attributes.Name.MAIN_CLASS.toString()] = application.mainClass
        }
    }

    shadowJar {
        minimize()
    }

    test {
        useJUnitPlatform()
    }

    jacocoTestCoverageVerification {
        dependsOn(test)

        violationRules {
            rule {
                limit {
                    minimum = 1.0.toBigDecimal()
                }
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = rootProject.name
            version = version.toString()
        }
    }
}

dokka {
    moduleName = property("name.display") as String

    dokkaSourceSets.main {
        sourceLink {
            val remoteVersion = if ((version as String).endsWith(VersionComparatorUtil.VersionTokenType.SNAPSHOT.name)) "main" else version

            localDirectory.set(File(property("kotlin.directory") as String))
            remoteUrl("${property("source.url.prefix")}${remoteVersion}/${localDirectory.get().asFile.relativeTo(rootProject.rootDir)}")
        }
    }

    pluginsConfiguration.html {
        homepageLink = property("homepage.url") as String

        footerMessage.set("© ${Calendar.getInstance().get(Calendar.YEAR)} ${property("authors")}")
    }
}
