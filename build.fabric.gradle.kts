plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT"
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

repositories {
    mavenCentral()
}

base.archivesName = "${property("mod_id")}-fabric-mc26.1"
version = property("mod_version").toString()

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${property("loader_version")}")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(25)
}

tasks.jar {
    from("LICENSE")
}

val modVersion = project.version.toString()
tasks.processResources {
    inputs.property("version", modVersion)
    filesMatching("fabric.mod.json") { expand(mapOf("version" to modVersion)) }
}

tasks.register<Copy>("collectJars") {
    group = "build"
    from(tasks.jar.map { it.archiveFile })
    into(rootProject.layout.buildDirectory.dir("libs"))
    dependsOn("build")
}

publishMods {
    val modrinthToken = System.getenv("MODRINTH_TOKEN") ?: ""
    val curseforgeToken = System.getenv("CURSEFORGE_TOKEN") ?: ""
    val githubToken = System.getenv("GITHUB_TOKEN") ?: ""

    file = tasks.jar.get().archiveFile
    dryRun = modrinthToken.isEmpty() || curseforgeToken.isEmpty() || githubToken.isEmpty()
    displayName = "${property("display_name")} ${project.version}"
    version = project.version.toString()
    changelog = rootProject.file("RELEASE_NOTE.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    val targets = property("supported_versions").toString().split(",")
    modrinth {
        projectId = "i090SePT"
        accessToken = modrinthToken
        targets.forEach(minecraftVersions::add)
    }
    curseforge {
        projectId = "648547"
        accessToken = curseforgeToken
        targets.forEach(minecraftVersions::add)
    }
    github {
        accessToken = githubToken
        parent(rootProject.tasks.named("publishGithub"))
    }
}
