plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.2.2"
}

group = "fi.sulku.hytale"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("libs/HytaleServer.jar"))
}

kotlin {
    jvmToolchain(25)
}

tasks.shadowJar {
    archiveClassifier.set("")

    relocate("kotlin", "fi.sulku.hytale.libs.kotlin")
    relocate("org.intellij.lang.annotations", "fi.sulku.hytale.libs.annotations")
    relocate("org.jetbrains.annotations", "fi.sulku.hytale.libs.jetbrains")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}