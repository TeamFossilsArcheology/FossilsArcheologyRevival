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

val forge_version: String by rootProject
val architectury_version: String by rootProject
val archives_base_name: String by rootProject

dependencies {
    forge("net.minecraftforge:forge:${forge_version}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury-forge:${architectury_version}")

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand("version" to project.version)
        }

        from(project(":common").sourceSets.main.get().resources)
    }

    shadowJar {
        exclude("fabric.mod.json")
        exclude("architectury.common.json")

        configurations = listOf(project.configurations["shadowCommon"])
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
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
        create<MavenPublication>("mavenForge") {
            artifactId = archives_base_name + "-" + project.name
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}