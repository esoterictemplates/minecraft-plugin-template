import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Calendar
import java.util.jar.Attributes

buildscript {
    dependencies {
        classpath(libs.dokka)
    }
}

plugins {
    application // Adds support for building a CLI application.

    `maven-publish`

    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.paperweight)

    alias(libs.plugins.shadow)

    alias(libs.plugins.dokka)
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    implementation(libs.utility)

    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jetbrains)
}

version = "1.1.1"
group = "dev.enderman"

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    withSourcesJar()
}

application {
    mainClass = "${group}.template.AppKt"
}

tasks {
    jar {
        manifest {
            attributes[Attributes.Name.MAIN_CLASS.toString()] = application.mainClass
        }
    }

    test {
        useJUnitPlatform()
    }

    withType<DokkaTask>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Calendar.getInstance().get(Calendar.YEAR)} Esoteric Enderman"
            homepageLink = "https://github.com/esoterictemplates/template-kotlin-repository"
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
