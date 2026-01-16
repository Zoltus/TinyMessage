plugins {
    java
    `maven-publish`
    id("hytale-mod") version "0.+"
}

group = "fi.sulku.hytale"
version = "2.0.1"

repositories {
    mavenCentral()
    maven("https://maven.hytale-modding.info/releases") {
        name = "HytaleModdingReleases"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}