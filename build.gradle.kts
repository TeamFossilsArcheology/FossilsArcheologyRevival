@file:Suppress("UnstableApiUsage")

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    java
}

val minecraftVersion: String by project
val minecraftVersionRange: String by project
val forgeLoaderVersionRange: String by project
val forgeVersionRange: String by project

val modName: String by project
val modId: String by project
val modTeam: String by project
val modLicense: String by project
val modDescription: String by project
val modVersion: String by project
val modAuthors: String by project

val mavenGroup: String by project
val archivesName: String by project

architectury {
    minecraft = minecraftVersion
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    val loom: LoomGradleExtensionAPI by extensions

    loom.silentMojangMappingsLicense()

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")

    version = modVersion
    group = mavenGroup

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    repositories {
        maven {
            name = "ParchmentMC"
            setUrl("https://maven.parchmentmc.org")
        }

        maven {
            setUrl("https://cursemaven.com")
            content {
                includeGroup("curse.maven")
            }
        }
        mavenLocal()
        maven {
            name = "Modrinth"
            setUrl("https://api.modrinth.com/maven")
            content {
                includeGroup("maven.modrinth")
            }
        }
        maven {
            name = "tterrag maven"
            setUrl("https://maven.tterrag.com/")
        }
        maven(url = "https://maven.ladysnake.org/releases")
        maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        maven(url = "https://dl.cloudsmith.io/public/darkpred-mods/more-hitboxes/maven/")
        maven(url = "https://maven.shedaniel.me/")
        maven(url = "https://maven.minecraftforge.net/")
    }

    // Include resources generated by data generators.
    sourceSets.main.configure {
        resources.srcDir("src/generated/resources")
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.release.set(17)
            options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
        }
        val props = mapOf(
            "minecraftVersion" to minecraftVersion, "minecraftVersionRange" to minecraftVersionRange,
            "modId" to modId, "modName" to modName, "modLicense" to modLicense, "modVersion" to modVersion,
            "modDescription" to modDescription, "modAuthors" to modAuthors, "fabricModAuthors" to modAuthors.split(", ").map { "\"" + it + "\"" }
        )

        processResources {
            inputs.properties(props)
            filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "fabric.mod.json")) {
                expand(props)
            }
        }

        jar {
            archiveBaseName.set("${archivesName}-${project.name.lowercase()}-${minecraftVersion}")
            manifest {
                attributes(mapOf(
                        "Specification-Title" to modName,
                        "Specification-Vendor" to modTeam,
                        "Specification-Version" to archiveVersion,
                        "Implementation-Title" to project.name,
                        "Implementation-Version" to archiveVersion,
                        "Implementation-Vendor" to modTeam,
                        "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
                ))
            }
        }
    }
}
