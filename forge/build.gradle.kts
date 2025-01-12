@file:Suppress("UnstableApiUsage")

import net.darkhax.curseforgegradle.TaskPublishCurseForge


plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.modrinth.minotaur") version "2.+"
    id("net.darkhax.curseforgegradle") version "1.1.25"
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
val moreHitboxesVersion: String by rootProject
val terraBlenderVersion: String by rootProject
val geckoLibVersion: String by project
val carryOnVersion: String by project
val farmersDelightVersion: String by project
val createVersion: String by project
val flywheelVersion: String by project
val registrateVersion: String by project

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${minecraftVersion}:$parchmentDate@zip")
    })

    //Required
    forge("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    modApi("dev.architectury:architectury-forge:${architecturyVersion}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }

    modImplementation("software.bernie.geckolib:geckolib-forge-1.18:${geckoLibVersion}")
    modImplementation("com.github.glitchfiend:TerraBlender-forge:${minecraftVersion}-${terraBlenderVersion}")
    modImplementation("com.github.darkpred.morehitboxes:morehitboxes-forge-${minecraftVersion}:${moreHitboxesVersion}")

    annotationProcessor("io.github.llamalad7:mixinextras-common:0.4.1")?.let { compileOnly(it) }
    include("io.github.llamalad7:mixinextras-forge:0.4.1")?.let { implementation(it) }

    //Optional
    modImplementation("maven.modrinth:jade:L2um3gq1")
    modImplementation("me.shedaniel:RoughlyEnoughItems-forge:${reiVersion}")
    modCompileOnly("maven.modrinth:carry-on:$carryOnVersion")
    modCompileOnly("maven.modrinth:farmers-delight:$farmersDelightVersion")

    modImplementation("com.simibubi.create:create-${minecraftVersion}:${createVersion}:slim") { isTransitive = false }
    modImplementation("com.jozufozu.flywheel:flywheel-forge-${minecraftVersion}:${flywheelVersion}")
    modImplementation("com.tterrag.registrate:Registrate:${registrateVersion}")

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
modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "no value"
    projectId = "IJY7IqPP"
    versionNumber.set(project.version.toString())
    versionType.set("release")
    uploadFile.set(tasks.remapJar)
    versionName = "${project.version} for Forge $minecraftVersion"
    debugMode = true
    dependencies {
        required.project("architectury-api")
        required.project("geckolib")
        required.project("terrablender")
        required.project("cardinal-components-api")
        required.project("more-hitboxes")
        embedded.project("sructurized-reborn")
        embedded.project("midnightlib")
    }
    changelog.set(rootProject.file("CHANGELOG.md").readText())
}

tasks.register<TaskPublishCurseForge>("publishCurseForge") {
    group = "publishing"
    description = "Publishes jar to CurseForge"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "no value"
    debugMode = true
    val mainFile = upload(223908, tasks.remapJar)

    mainFile.changelog = rootProject.file("CHANGELOG.md").readText()
    mainFile.changelogType = "markdown"
    mainFile.releaseType = "release"
    mainFile.addRequirement("architectury-api", "geckolib", "terrablender", "cardinal-components-api", "more-hitboxes")
    mainFile.addEmbedded("midnightlib")
}

tasks.named("publish") {
    finalizedBy("modrinth", "publishCurseForge")
}
