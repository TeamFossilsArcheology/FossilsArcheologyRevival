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

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modApi("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury-fabric:${architectury_version}")

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
        exclude("architectury.common.json")

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
