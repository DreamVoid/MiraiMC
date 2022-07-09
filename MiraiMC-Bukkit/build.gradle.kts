import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
}

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}


dependencies {
    implementation(project(":MiraiMC-Base"))
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")?.because("maven")
    compileOnly("com.google.guava:guava:31.1-jre")?.because("maven")
}

tasks.withType<ShadowJar> {
    val libs = rootProject.extra["shadowLibrariesPackage"].toString()

    relocate("com.zaxxer",  "$libs.com.zaxxer")
    relocate("org.apache", "$libs.org.apache")
    relocate("org.slf4j", "$libs.org.slf4j")
}
