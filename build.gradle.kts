import xyz.jpenilla.resourcefactory.paper.paperPluginYaml
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

    alias(libs.plugins.bukkit.convention)
    alias(libs.plugins.paper.run)

    alias(libs.plugins.shadow)

    alias(libs.plugins.dokka)
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    implementation(libs.utility)

    testRuntimeOnly(libs.junit.platform)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jetbrains)

    testImplementation(libs.bukkit.mock)
}

description = "A template."

version = "1.1.1"
group = "dev.enderman"

val yaml = paperPluginYaml {
  name = "Template"
  description = project.description

  authors = listOfNotNull("Esoteric Foundation", "Esoteric Enderman")

  setVersion(project.version)

  website = "https://github.com/esoterictemplate/template-minecraft-plugin"

  apiVersion = "1.21.4"

  main = "${project.group}.minecraft.plugins.template.TemplatePlugin"
  bootstrapper = "${project.group}.minecraft.plugins.template.bootstrap.TemplatePluginBootstrap"
}

bukkitPluginYaml {
    main = yaml.main
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
    withType<AbstractRun> {
        javaLauncher = project.javaToolchains.launcherFor {
            vendor = JvmVendorSpec.JETBRAINS
            languageVersion = JavaLanguageVersion.of(21)
        }

        jvmArgs("-XX:+AllowEnhancedClassRedefinition")
    }

    test {
        useJUnitPlatform()
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
