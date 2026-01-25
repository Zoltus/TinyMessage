plugins {
    java
    `maven-publish`
}

group = "fi.sulku.hytale"
version = "2.0.0"
val hytaleVersion = "2026.01.24-6e2d4fc36"

repositories {
    mavenCentral()
    maven("https://maven.hytale.com/release")
}

dependencies {
    compileOnly("com.hypixel.hytale:Server:${hytaleVersion}")
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
