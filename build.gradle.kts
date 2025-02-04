plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    id("com.gradleup.shadow") version "9.0.0-beta6"

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.github.esotericfoundation:utility.kt:1.1.1")
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
            attributes["Main-Class"] = application.mainClass.get()
        }
    }

    named<Test>("test") {
        useJUnitPlatform()
    }
}
