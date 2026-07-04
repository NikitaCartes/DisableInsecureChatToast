pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.2"
}

stonecutter {
    create(rootProject) {
        versions("26-fabric" to "26.1").buildscript("build.fabric.gradle.kts")
        versions("26-neoforge" to "26.1").buildscript("build.neoforge.gradle.kts")
        vcsVersion = "26-fabric"
    }
}
