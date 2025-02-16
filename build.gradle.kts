import xyz.jpenilla.runtask.task.AbstractRun
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Calendar

buildscript {
    dependencies {
        classpath(libs.dokka)
    }
}

plugins {
    `maven-publish`

    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.paperweight)

    alias(libs.plugins.paper.convention)
    alias(libs.plugins.paper.run)

    alias(libs.plugins.shadow)

    alias(libs.plugins.dokka)
}

paperPluginYaml {
    name = "Template"
    description = project.description

    authors = listOfNotNull("Esoteric Foundation", "Esoteric Enderman")

    setVersion(project.version)

    website = "https://github.com/esoterictemplate/template-minecraft-plugin"

    apiVersion = project.property("minecraft.version") as String

    main = "${project.group}.${project.property("package")}.TemplatePlugin"
    bootstrapper = "${project.group}.${project.property("package")}.bootstrap.TemplatePluginBootstrap"
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("${paperPluginYaml.apiVersion.get()}-R0.1-SNAPSHOT")

    implementation(libs.utility)

    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jetbrains)

    testImplementation(libs.bukkit.mock)
}

paperweight {
    addServerDependencyTo = configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME).map { setOf(it) }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    withSourcesJar()
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    test {
        useJUnitPlatform()
    }

    withType<AbstractRun> {
        javaLauncher = project.javaToolchains.launcherFor {
            vendor = JvmVendorSpec.JETBRAINS
            languageVersion = JavaLanguageVersion.of(21)
        }

        jvmArgs("-XX:+AllowEnhancedClassRedefinition")
    }

    withType<DokkaTask> {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Calendar.getInstance().get(Calendar.YEAR)} Esoteric Enderman"
            homepageLink = "https://github.com/esoterictemplates/template-minecraft-plugin"
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
