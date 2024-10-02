@file:Suppress("UnstableApiUsage")

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

architectury {
    platformSetupLoomIde()
    forge()
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    named("developmentForge").get().extendsFrom(common)
}

val minecraftVersion: String by rootProject
val forgeVersion: String by project
val architecturyVersion: String by rootProject
val archivesBaseName: String by rootProject
val parchmentDate: String by rootProject
val reiVersion: String by rootProject
val multiPartLibVersion: String by rootProject
val terraBlenderVersion: String by rootProject
val geckoLibVersion: String by project

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${minecraftVersion}:$parchmentDate@zip")
    })

    forge("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury-forge:${architecturyVersion}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }

    modImplementation("me.shedaniel:RoughlyEnoughItems-forge:${reiVersion}")
    modImplementation("software.bernie.geckolib:geckolib-forge-1.18:${geckoLibVersion}")
    modImplementation("com.github.glitchfiend:TerraBlender-forge:${minecraftVersion}-${terraBlenderVersion}")
    modImplementation("com.github.darkpred.multipartsupport:multipartsupport-forge:${minecraftVersion}-${multiPartLibVersion}")

    annotationProcessor("io.github.llamalad7:mixinextras-common:0.4.1")?.let { compileOnly(it) }
    include("io.github.llamalad7:mixinextras-forge:0.4.1")?.let { implementation(it) }
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfig("fossil_forge.mixins.json")

        dataGen { //breaks my forge run, so I disable it when not needed
            mod(archivesBaseName)
        }
        /*
        mixinConfig("fa-common.mixins.json")
        mixinConfig("fa-forge.mixins.json")

        let's use these when we actually need it
        */
    }
}

tasks {
    processResources {
        from(project(":common").sourceSets.main.get().resources)
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    shadowJar {
        exclude("fabric.mod.json")

        configurations = listOf(project.configurations["shadowCommon"])
        archiveClassifier.set("forge-dev-shadow")
        archiveBaseName.set(archivesBaseName)
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set("forge")
        archiveBaseName.set(archivesBaseName)
    }

    jar {
        archiveClassifier.set("dev")
    }
}

val javaComponent = components["java"] as AdhocComponentWithVariants
javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
    skip()
}

publishing {
    publications {
        create<MavenPublication>("mavenForge") {
            artifactId = archivesBaseName + "-" + project.name
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}