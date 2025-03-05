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
val modVersion: String by rootProject
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
val alexsMobsVersion: String by project
val createVersion: String by project
val flywheelVersion: String by project
val registrateVersion: String by project

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings() {
            nameSyntheticMembers = true
        }
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
    //(Optional) FD & Addons
    modCompileOnly("maven.modrinth:farmers-delight:$farmersDelightVersion")
    modCompileOnly("curse.maven:oceans-delight-841262:4462935") //1.18.2 1.0.0
    modCompileOnly("curse.maven:nethers-delight-496394:3756127") //1.18.2 2.2.0
    modCompileOnly("curse.maven:delightful-637529:4275637") //1.18.2 3.2.1
    modCompileOnly("curse.maven:oh-the-biomes-youll-go-247560:4971536") //1.18.2 1.5.1, for delightful
    modCompileOnly("curse.maven:ars-nouveau-401955:4543053") //1.18.2 2.9.0, for delightful
    modCompileOnly("curse.maven:enders-delight-827163:4668841") //1.18.2 1.2.1
    modCompileOnly("curse.maven:ends-delight-662675:4675472") //1.18.2 1.2.1
    modCompileOnly("curse.maven:crabbers-delight-892827:4762846") //1.18.2 1.1.2
    modCompileOnly("curse.maven:miners-delight-plus-689630:4738271") //1.18.2 1.1.2
    modCompileOnly("curse.maven:corn-delight-577805:4000542") //1.18.2 1.0.6
    modCompileOnly("curse.maven:mysterious-mountain-lib-368098:5414927") //For corn delight
    modCompileOnly("curse.maven:cultural-delights-574622:4000179") //1.18.2 0.14
    modCompileOnly("curse.maven:pineapple-delight-687974:4554347") //1.18.2 1.0.6 Fix1
    modCompileOnly("curse.maven:large-meals-an-add-on-for-farmers-delight-625110:4032025") //1.18.2 1.2.0
    modCompileOnly("curse.maven:festive-delight-711672:4707741") //1.18.2 1.1
    modCompileOnly("maven.modrinth:butchers-delight:1.18.22.1.0") //1.18.2 2.1.0
    modCompileOnly("maven.modrinth:butchers-delight-foods:1.18.21.0.3") //1.18.2 2.1.0
    modCompileOnly("curse.maven:coffee-delight-835597:4707181") //1.18.2 1.4
    modCompileOnly("curse.maven:casualness-delight-909519:5194377") //1.18.2 0.4
    modCompileOnly("curse.maven:italians-delight-669167:4435561") //1.18.2 1.5
    modCompileOnly("curse.maven:seed-delight-1016377:5736715") //1.18.2 1.0.1
    modCompileOnly("curse.maven:argentinasdelight-831127:4818251") //1.18.2 3.0 beta
    modCompileOnly("curse.maven:honey-expansion-add-on-for-farmers-delight-541951:3751480") //1.18.2 1.1.1
    modCompileOnly("curse.maven:brewin-and-chewin-637808:3851036") //1.18.2 1.0.0
    modCompileOnly("curse.maven:alexs-delight-556448:3984913") //1.18.2 1.3.3
    modCompileOnly("curse.maven:farmers-respite-551453:3859156") //1.18.2 1.3.0
    //modCompileOnly("curse.maven:citadel-331936:3783096") //For alex's delight/alex's mobs
    //modCompileOnly("curse.maven:curios-309927:4985315") //For ars nouveau
    //modCompileOnly("curse.maven:patchouli-306770:3846086") //For ars nouveau
    modCompileOnly("maven.modrinth:alexs-mobs:$alexsMobsVersion")
    modCompileOnly("curse.maven:radium-570017:3707226")

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
        archiveAppendix.set("forge-$minecraftVersion")
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
    token = "${project.property("MODRINTH_TOKEN") ?: "no value"}"
    projectId = "IJY7IqPP"
    versionNumber.set("$minecraftVersion-$modVersion-${project.name}")
    versionType.set("release")
    uploadFile.set(tasks.remapJar)
    versionName = "$modVersion for Forge $minecraftVersion"
    debugMode = true
    dependencies {
        required.project("architectury-api")
        required.project("geckolib")
        required.project("terrablender")
        required.project("more-hitboxes")
    }
    changelog.set(rootProject.file("CHANGELOG.md").readText())
}

tasks.register<TaskPublishCurseForge>("publishCurseForge") {
    group = "publishing"
    description = "Publishes jar to CurseForge"
    apiToken = project.property("CURSEFORGE_TOKEN") ?: "no value"
    debugMode = true
    val mainFile = upload(223908, tasks.remapJar)
    mainFile.displayName = "$modVersion for Forge $minecraftVersion"
    mainFile.changelog = rootProject.file("CHANGELOG.md").readText()
    mainFile.addEnvironment("Forge")
    mainFile.changelogType = "markdown"
    mainFile.releaseType = "release"
    mainFile.addRequirement("architectury-api", "geckolib", "terrablender", "more-hitboxes")
}

tasks.named("publish") {
    finalizedBy("modrinth", "publishCurseForge")
}
