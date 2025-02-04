plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.shadow)

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    testImplementation(libs.junit.jetbrains)

    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly(libs.junit.platform)

    implementation(libs.utility)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "dev.enderman.template.AppKt"
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = application.mainClass
        }
    }

    test {
        useJUnitPlatform()
    }
}
