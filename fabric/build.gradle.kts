@file:Suppress("UnstableApiUsage")

import net.darkhax.curseforgegradle.TaskPublishCurseForge
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
    id("com.modrinth.minotaur") version "2.+"
    id("net.darkhax.curseforgegradle") version "1.1.25"
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
val modVersion: String by rootProject
val fabricLoaderVersion: String by rootProject
val fabricApiVersion: String by project
val architecturyVersion: String by rootProject
val archivesBaseName: String by rootProject
val parchmentDate: String by rootProject
val reiVersion: String by rootProject
val moreHitboxesVersion: String by rootProject
val terraBlenderVersion: String by rootProject
val cardinalComponentsVersion: String by project
val energyVersion: String by project
val midnightConfigVersion: String by project
val geckoLibVersion: String by project
val farmersDelightVersion: String by project

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${minecraftVersion}:$parchmentDate@zip")

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

    //Required
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    modApi("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}+${minecraftVersion}")
    modApi("dev.architectury:architectury-fabric:${architecturyVersion}")
    modApi("teamreborn:energy:${energyVersion}")
    include("teamreborn:energy:${energyVersion}")
    modImplementation("maven.modrinth:Wd844r7Q:1.18.2-02")//Structurized Reborn
    include("maven.modrinth:Wd844r7Q:1.18.2-02")//Structurized Reborn
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.18:${geckoLibVersion}")
    modImplementation("com.github.glitchfiend:TerraBlender-fabric:${minecraftVersion}-${terraBlenderVersion}")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinalComponentsVersion}")
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${cardinalComponentsVersion}")
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinalComponentsVersion}")
    include("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${cardinalComponentsVersion}")
    modImplementation("maven.modrinth:midnightlib:${midnightConfigVersion}")
    include("maven.modrinth:midnightlib:${midnightConfigVersion}")
    modImplementation("com.github.darkpred.morehitboxes:morehitboxes-fabric-${minecraftVersion}:${moreHitboxesVersion}")

    //Optional
    modRuntimeOnly("curse.maven:modmenu-308702:4145213")
    modCompileOnly("maven.modrinth:jade:MSJGBHIo")
    modRuntimeOnly("maven.modrinth:jade:MSJGBHIo")
    modImplementation("me.shedaniel:RoughlyEnoughItems-fabric:${reiVersion}")
    modCompileOnly("maven.modrinth:farmers-delight-fabric:$farmersDelightVersion")

    //Dev only
    /*
    modRuntimeOnly("curse.maven:hugestructureblocks-474114:3647042")
    modRuntimeOnly("curse.maven:worldedit-225608:3697298")
    modRuntimeOnly("maven.modrinth:smoothboot-fabric:1.18.2-1.7.0")
    modRuntimeOnly("curse.maven:commandstructures-565119:3733097")
    modRuntimeOnly("maven.modrinth:betterf3:qUyRV6XT")
    modRuntimeOnly("maven.modrinth:lazydfu:0.1.2")
    //modRuntimeOnly("maven.modrinth:sodium:mc1.18.2-0.4.1")
    //modRuntimeOnly("maven.modrinth:indium:1.0.7+mc1.18.2")
    //runtimeOnly("org.joml:joml:1.10.4")
    modRuntimeOnly("maven.modrinth:modmenu:3.2.5")
    modRuntimeOnly("curse.maven:camerautils-510234:3667404")
    modRuntimeOnly("curse.maven:replay-775651:4262559")
    //modRuntimeOnly("maven.modrinth:6pku8gW1:zbBHXeFQ")//Energized Power
    modRuntimeOnly("curse.maven:debugutils-783010:5337485")
    //modRuntimeOnly("com.github.darkpred.extended_structure_blocks:extended-structure-blocks-fabric:${minecraftVersion}-0.4.0")
    */
    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

tasks {
    processResources {
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
        archiveAppendix.set("fabric-$minecraftVersion")
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
    versionNumber.set("$minecraftVersion-$modVersion-${project.name}")
    versionType.set("release")
    uploadFile.set(tasks.remapJar)
    versionName = "$modVersion for Fabric $minecraftVersion"
    debugMode = true
    dependencies {
        required.project("fabric-api")
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
    mainFile.addRequirement("fabric-api", "architectury-api", "geckolib", "terrablender", "cardinal-components-api", "more-hitboxes")
    mainFile.addEmbedded("midnightlib")
}

tasks.named("publish") {
    finalizedBy("modrinth", "publishCurseForge")
}
