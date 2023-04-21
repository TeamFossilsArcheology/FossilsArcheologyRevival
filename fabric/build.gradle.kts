@file:Suppress("UnstableApiUsage")

import net.fabricmc.loom.api.mappings.layered.MappingContext
import net.fabricmc.loom.api.mappings.layered.MappingLayer
import net.fabricmc.loom.api.mappings.layered.MappingsNamespace
import net.fabricmc.loom.api.mappings.layered.spec.MappingsSpec
import net.fabricmc.loom.configuration.providers.mappings.intermediary.IntermediaryMappingLayer
import net.fabricmc.mappingio.MappingVisitor
import net.fabricmc.mappingio.tree.MappingTreeView
import net.fabricmc.mappingio.tree.MemoryMappingTree

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    named("developmentFabric").get().extendsFrom(common)
}

val minecraftVersion: String by rootProject
val fabricLoaderVersion: String by rootProject
val fabricApiVersion: String by rootProject
val architecturyVersion: String by rootProject
val archivesBaseName: String by rootProject
val parchmentDate: String by rootProject
val clothConfigVersion: String by rootProject
val reiVersion: String by rootProject
val terraBlenderVersion: String by rootProject
val cardinalComponentsVersion: String by project

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.18.2:$parchmentDate@zip")

        addLayer(object : MappingsSpec<MappingLayer> {
            val getClasses = MappingTreeView::class.java.getDeclaredMethod("getClasses")
                .apply { isAccessible = true }
            val getMethods = MappingTreeView.ClassMappingView::class.java.getDeclaredMethod("getMethods")
                .apply { isAccessible = true }
            val getName = MappingTreeView.ElementMappingView::class.java.getDeclaredMethod("getName", String::class.java)
                .apply { isAccessible = true }
            val entryClass = Class.forName("net.fabricmc.mappingio.tree.MemoryMappingTree\$Entry")
            val srcNameField = entryClass.getDeclaredField("srcName")
                .apply { isAccessible = true }

            val METHOD_NAME_MAP = mapOf("getTextureLocation" to "_getTextureLocation")
            override fun createLayer(context: MappingContext?): MappingLayer {
                return object : MappingLayer {
                    override fun visit(mappingVisitor: MappingVisitor?) {
                        val memoryMappingTree = mappingVisitor as MemoryMappingTree
                        (getClasses(memoryMappingTree) as Collection<*>).forEach { classEntry ->
                            (getMethods(classEntry) as Collection<*>).forEach { methodEntry ->
                                METHOD_NAME_MAP[getName(methodEntry, MappingsNamespace.NAMED.toString())]?.let {
                                    srcNameField[methodEntry] = it
                                }
                            }
                        }
                    }

                    override fun getSourceNamespace() = MappingsNamespace.NAMED

                    override fun dependsOn() = listOf(IntermediaryMappingLayer::class.java)
                }
            }

            override fun hashCode() = METHOD_NAME_MAP.hashCode()
        })
    })

    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    modApi("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury-fabric:${architecturyVersion}")
    modRuntimeOnly("curse.maven:modmenu-308702:4145213")
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${clothConfigVersion}")
    modImplementation("me.shedaniel:RoughlyEnoughItems-fabric:${reiVersion}")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.18:3.0.80")
    modImplementation("com.github.glitchfiend:TerraBlender-fabric:${minecraftVersion}-${terraBlenderVersion}")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinalComponentsVersion}")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${cardinalComponentsVersion}")
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinalComponentsVersion}")
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${cardinalComponentsVersion}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }

        from(project(":common").sourceSets.main.get().resources)
    }

    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier.set("fabric-dev-shadow")
        archiveBaseName.set(archivesBaseName)
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set("fabric")
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
        create<MavenPublication>("mavenFabric") {
            artifactId = archivesBaseName + "-" + project.name
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}
