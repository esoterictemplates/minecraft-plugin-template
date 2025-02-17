import org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil
import java.util.Calendar
import java.util.jar.Attributes

plugins {
    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.release.axion)

    alias(libs.plugins.paperweight)

    alias(libs.plugins.paper.convention)
    alias(libs.plugins.paper.run)

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
kotlin {
    jvmToolchain((property("java.version") as String).toInt())
}

java {
    withSourcesJar()
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    withType<Jar> {
        archiveBaseName = rootProject.name
    }

    shadowJar {
        minimize()
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

        footerMessage.set("© ${Calendar.getInstance().get(Calendar.YEAR)} ${property("author")}")
    }
}
