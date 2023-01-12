@file:Suppress("UnstableApiUsage")

val enabledPlatforms: String by rootProject
val fabricLoaderVersion: String by rootProject
val architecturyVersion: String by rootProject
val archivesBaseName: String by rootProject
val parchmentDate: String by rootProject

architectury {
    common(enabledPlatforms.split(","))
}

loom {
    accessWidenerPath.set(file("src/main/resources/fossil.accesswidener"))
}

dependencies {
    "mappings"(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.18.2:$parchmentDate@zip")
    })
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    // Remove the next line if you don't want to depend on the API
    modApi("dev.architectury:architectury:${architecturyVersion}")

    // Geckolib 3.0 template uses forge one so... I guess this is ok?
    modImplementation("software.bernie.geckolib:geckolib-forge-1.18:3.0.57")
}

publishing {
    publications {
        create<MavenPublication>("mavenCommon") {
            artifactId = archivesBaseName
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
    }
}
