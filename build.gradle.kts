/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our samples at https://docs.gradle.org/8.1.1/samples
 */

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

configurations.all {
    resolutionStrategy.capabilitiesResolution.withCapability("org.bukkit:bukkit"){
        select("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    }
}

version = "0.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.22.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.googlecode.json-simple:json-simple:1.1")
    implementation("com.google.inject:guice:7.0.0")
}

tasks {
    build{
        dependsOn("shadowJar")
    }
    shadowJar{
        minimize()
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_21
    sourceCompatibility = JavaVersion.VERSION_21
}
