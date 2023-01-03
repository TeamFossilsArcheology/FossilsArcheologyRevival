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

val fabric_loader_version: String by rootProject
val fabric_api_version: String by rootProject
val architectury_version: String by rootProject
val archives_base_name: String by rootProject
val parchment_date: String by rootProject

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.18.2:$parchment_date@zip")

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

    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modApi("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury-fabric:${architectury_version}")
    modRuntimeOnly("curse.maven:modmenu-308702:4145213")
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.18:3.0.80")

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
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set(null as String?)
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
            artifactId = archives_base_name + "-" + project.name
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}
